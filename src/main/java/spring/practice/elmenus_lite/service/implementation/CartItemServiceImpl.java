package spring.practice.elmenus_lite.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemRequestDto;
import spring.practice.elmenus_lite.dto.UpdateItemQuantityRequest;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.Customer;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.handlerException.DatabaseOperationException;
import spring.practice.elmenus_lite.mapper.CartItemMapper;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.service.CartItemService;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;

@Log4j2
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartItemMapper cartItemMapper;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, MenuItemRepository menuItemRepository, CartRepository cartRepository, CustomerRepository customerRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Transactional
    @Override
    public void addCartItem(CartItemRequestDto cartItemRequestDto) {
        log.info("Adding item to cart - CustomerID: {}, MenuItemID: {}", cartItemRequestDto.getCustomerId(), cartItemRequestDto.getMenuItemId());

        Customer customer = getCustomerById(cartItemRequestDto.getCustomerId());
        Cart cart = getOrCreateCartByCustomer(customer);
        MenuItem menuItem = getMenuItemById(cartItemRequestDto.getMenuItemId());

        CartItem cartItem = getCartItem(cartItemRequestDto.getQuantity(), cart, menuItem);
        try {

            cartItemRepository.save(cartItem);
        } catch (RuntimeException ex) {
            log.error(CAN_NOT_ADD_CART_ITEM.getMessage(), ex);
            throw new DatabaseOperationException(CAN_NOT_ADD_CART_ITEM.getMessage(), ex);
        }
    }

    private static CartItem getCartItem(int quantity, Cart cart, MenuItem menuItem) {
        return CartItem.builder()
                .cart(cart)
                .menuItem(menuItem)
                .quantity(quantity)
                .build();
    }

    private Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new EntityNotFoundException(CUSTOMER_NOT_FOUND.getMessage() + customerId));
    }

    private MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException(Menu_ITEM_NOT_FOUND.getMessage() + menuItemId));
    }

    private Cart getOrCreateCartByCustomer(Customer customer) {
        return cartRepository.findByCustomerCustomerId(customer.getCustomerId())
                .orElseGet(() -> createCart(customer));
    }

    private Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cartRepository.save(cart);
    }


    /**
     * Retrieves a CartItem by its ID.
     *
     * @param id the ID of the CartItem to retrieve
     * @return the CartItem with the specified ID
     * @throws RuntimeException if the CartItem with the given ID is not found
     */
    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CART_ITEM_NOT_FOUND.getMessage() + id));
    }

    @Override
    public void deleteCartItemById(Long cartItemId) {
        log.info("Delete cart item with ID {}", cartItemId);

        if (isCartItemExist(cartItemId)) {
            try {
                cartItemRepository.deleteById(cartItemId);
            } catch (RuntimeException e) {
                log.error(CAN_NOT_DELETE_CART_ITEM.getMessage(), e);
                throw new DatabaseOperationException(CAN_NOT_DELETE_CART_ITEM.getMessage(), e);
            }
        }
    }

    private boolean isCartItemExist(Long cartItemId) {
        if (this.cartItemRepository.existsById(cartItemId)) {
            return true;
        } else {
            log.error("{} {}", CART_ITEM_NOT_FOUND.getMessage(), cartItemId);
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND.getMessage() + cartItemId);
        }
    }

    /**
     * Updates the quantity of a CartItem based on the provided request.
     *
     * @param updateItemQuantityRequest the request containing the CartItem ID and the new quantity
     * @return the updated CartItem as a DTO
     * @throws EntityNotFoundException if the CartItem with the given ID is not found
     * @throws DatabaseOperationException  if there is an error during the save operation
     */
    @Override
    public CartItemDto updateCartItemQuantity(UpdateItemQuantityRequest updateItemQuantityRequest) {
        CartItem cartItem = getCartItemById(updateItemQuantityRequest.getCartItemId());
        cartItem.setQuantity(updateItemQuantityRequest.getQuantity());
        try {
            CartItem updatedItem = cartItemRepository.save(cartItem);
            return this.cartItemMapper.cartItemToCartItemDto(updatedItem);
        } catch (DatabaseOperationException e) {
            log.error(CAN_NOT_UPDATE_QUANTITY.getMessage());
            throw new DatabaseOperationException(CAN_NOT_UPDATE_QUANTITY.getMessage() + " " + e.getMessage(), e);
        }
    }
}
