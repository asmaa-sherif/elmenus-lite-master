package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartItemDto;

//add, delete , update
public interface CartItemService {
    CartItemDto addCartItem(CartItemDto cartItemDto);

    CartItemDto getCartItemById(Long id);

    void deleteCartItemById(Long id);

    CartItemDto updateCartItemById(Long id, CartItemDto cartItemDto);

    CartItemDto updateCartItemQuantity(Long cartId, Long cartItemId, Integer quantity);
}
