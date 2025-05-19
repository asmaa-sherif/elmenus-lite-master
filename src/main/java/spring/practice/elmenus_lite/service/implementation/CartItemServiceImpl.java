package spring.practice.elmenus_lite.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.mapper.CartItemMapper;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartItemMapper cartItemMapper;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, MenuItemRepository menuItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = mapToCartItem(cartItemDto);
        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToCartItemResponseDto(savedItem);
    }

    @Override
    public CartItemDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));
        return mapToCartItemResponseDto(cartItem);
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
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
