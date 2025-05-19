package spring.practice.elmenus_lite.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.Customer;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.mapper.CartItemMapper;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.service.CartItemService;

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

    @Override
    public void addCartItem(Long customerId, Long menuItemId, Integer quantity) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseGet(() -> {
                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new NotFoundCustomException("Customer not found"));
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundCustomException("MenuItem not found"));

        CartItem cartItem = new CartItem(cart, menuItem, quantity);
        cartItemRepository.save(cartItem);
    }



    @Override
    public CartItemDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));
        return mapToCartItemResponseDto(cartItem);
    }

    @Override
    public Boolean deleteCartItemById(Long id) {
        if (!this.cartItemRepository.existsById(id)) {
            throw new NotFoundCustomException("Cart item not found");
        }
        cartItemRepository.deleteById(id);
        return true;
    }


    @Override
    public CartItemDto updateCartItemById(Long id, CartItemDto cartItemDto) {
        CartItem existingItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        existingItem.setQuantity(cartItemDto.getQuantity());

        MenuItem menuItem = menuItemRepository.findById(cartItemDto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + cartItemDto.getMenuItemId()));

        existingItem.setMenuItem(menuItem);

        CartItem updatedItem = cartItemRepository.save(existingItem);
        return mapToCartItemResponseDto(updatedItem);
    }

    @Override
    public CartItemDto updateCartItemQuantity(Long cartId, Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findByCartItemIdAndCartCartId(cartItemId, cartId)
                .orElseThrow(() -> new NotFoundCustomException("Cart Item with id: " + cartItemId + " not found in this cart " + cartId));
        cartItem.setQuantity(quantity);
        CartItem updatedItem = cartItemRepository.save(cartItem);
        return mapToCartItemResponseDto(updatedItem);
    }


    // Mapper from Entity to DTO
    private CartItemDto mapToCartItemResponseDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setProductName(cartItem.getMenuItem().getItemName());  // assuming MenuItem has getItemName()
        dto.setPrice(cartItem.getMenuItem().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setTotal(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());
        dto.setMenuItemId(cartItem.getMenuItem().getMenuItemId()); // add this field to your DTO!
        return dto;
    }

    // Mapper from DTO to Entity
    private CartItem mapToCartItem(CartItemDto dto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + dto.getMenuItemId()));

        cartItem.setMenuItem(menuItem);

        return cartItem;
    }
}
