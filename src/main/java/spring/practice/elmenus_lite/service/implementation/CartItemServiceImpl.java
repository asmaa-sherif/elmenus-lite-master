package spring.practice.elmenus_lite.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
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
        //TODO: check if the cart exist add the item
        // TODO: else add cart then add item on the cart
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

        MenuItem menuItem = menuItemRepository.findById(cartItemDto.getMenuItem().getMenuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + cartItemDto.getMenuItem().getMenuItemId()));

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
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setMenuItemId(cartItem.getMenuItem().getMenuItemId());
        menuItemDto.setItemName(cartItem.getMenuItem().getItemName());
        menuItemDto.setPrice(cartItem.getMenuItem().getPrice());
        dto.setMenuItem(menuItemDto); // add this field to your DTO!
        return dto;
    }

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
