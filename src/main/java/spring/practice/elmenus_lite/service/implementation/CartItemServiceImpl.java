package spring.practice.elmenus_lite.service.implementation;

import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, MenuItemRepository menuItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public CartItemResponseDto addCartItem(CartItemResponseDto cartItemDto) {
        CartItem cartItem = mapToCartItem(cartItemDto);
        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToCartItemResponseDto(savedItem);
    }

    @Override
    public CartItemResponseDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));
        return mapToCartItemResponseDto(cartItem);
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItemResponseDto updateCartItemById(Long id, CartItemResponseDto cartItemDto) {
        CartItem existingItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        existingItem.setQuantity(cartItemDto.getQuantity());

        MenuItem menuItem = menuItemRepository.findById(cartItemDto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + cartItemDto.getMenuItemId()));

        existingItem.setMenuItem(menuItem);

        CartItem updatedItem = cartItemRepository.save(existingItem);
        return mapToCartItemResponseDto(updatedItem);
    }


    // Mapper from Entity to DTO
    private CartItemResponseDto mapToCartItemResponseDto(CartItem cartItem) {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setProductName(cartItem.getMenuItem().getItemName());  // assuming MenuItem has getItemName()
        dto.setPrice(cartItem.getMenuItem().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setTotal(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());
        dto.setMenuItemId(cartItem.getMenuItem().getMenuItemId()); // add this field to your DTO!
        return dto;
    }

    // Mapper from DTO to Entity
    private CartItem mapToCartItem(CartItemResponseDto dto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + dto.getMenuItemId()));

        cartItem.setMenuItem(menuItem);

        return cartItem;
    }
}
