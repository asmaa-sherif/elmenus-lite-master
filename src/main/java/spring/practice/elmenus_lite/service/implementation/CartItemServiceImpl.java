package spring.practice.elmenus_lite.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemRequestDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.Customer;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.handlerException.SaveOperationException;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.service.CartItemService;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;

@Log4j2
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, MenuItemRepository menuItemRepository, CartRepository cartRepository, CustomerRepository customerRepository) {
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public void addCartItem(CartItemRequestDto dto) {
        Customer customer = getCustomerById(dto.getCustomerId());
        Cart cart = getOrCreateCartByCustomer(customer);
        MenuItem menuItem = getMenuItemById(dto.getMenuItemId());

        CartItem cartItem = new CartItem(cart, menuItem, dto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    private Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
    }

    private MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found: " + menuItemId));
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
    public Boolean deleteCartItemById(Long cartItemId) {
        // TODO: extract this to a method
        //  this.cartItemRepository.existsById(id)
        if (!isCartItemExist(cartItemId)) {
            throw new EntityNotFoundException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId);
        return true;
    }

    private Boolean isCartItemExist(Long cartItemId){
       return this.cartItemRepository.existsById(cartItemId);
    }


    @Override
    public CartItemDto updateCartItemById(Long id, CartItemDto cartItemDto) {
        // TODO: extract this to a method
        CartItem existingItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        existingItem.setQuantity(cartItemDto.getQuantity());

        // TODO: use service instead of the repo
        MenuItem menuItem = menuItemRepository.findById(cartItemDto.getMenuItem().getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + cartItemDto.getMenuItem().getMenuItemId()));

        existingItem.setMenuItem(menuItem);

        CartItem updatedItem = cartItemRepository.save(existingItem);
        return mapToCartItemResponseDto(updatedItem);
    }

    /**
     * Updates the quantity of a CartItem based on the provided request.
     *
     * @param cartItemRequest the request containing the CartItem ID and the new quantity
     * @return the updated CartItem as a DTO
     * @throws EntityNotFoundException if the CartItem with the given ID is not found
     * @throws SaveOperationException if there is an error during the save operation
     */
    @Override
    public CartItemDto updateCartItemQuantity(CartItemRequestDto cartItemRequest) {
        CartItem cartItem = getCartItemById(cartItemRequest.getCartItemId());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        try {
            CartItem updatedItem = cartItemRepository.save(cartItem);
            return mapToCartItemResponseDto(updatedItem);
        } catch (SaveOperationException e) {
            log.error(CAN_NOT_UPDATE_QUANTITY.getMessage());
            throw new SaveOperationException(CAN_NOT_UPDATE_QUANTITY.getMessage() + " " + e.getMessage(), e);
        }
    }

    // TODO: use map struct
    // Mapper from Entity to DTO
    private CartItemDto mapToCartItemResponseDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setTotal(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setMenuItemId(cartItem.getMenuItem().getMenuItemId());
        menuItemDto.setItemName(cartItem.getMenuItem().getItemName());
        menuItemDto.setPrice(cartItem.getMenuItem().getPrice());
        menuItemDto.setItemName(cartItem.getMenuItem().getItemName());  // assuming MenuItem has getItemName()
        menuItemDto.setPrice(cartItem.getMenuItem().getPrice());
        dto.setMenuItem(menuItemDto); // add this field to your DTO!
        return dto;
    }

    // TODO: use map struct
    // Mapper from DTO to Entity
    private CartItem mapToCartItem(CartItemDto dto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItem().getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + dto.getMenuItem().getMenuItemId()));

        cartItem.setMenuItem(menuItem);

        return cartItem;
    }
}
