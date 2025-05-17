package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartItemResponseDto;

//add, delete , update
public interface CartItemService {
    CartItemResponseDto addCartItem(CartItemResponseDto cartItemDto);
    CartItemResponseDto getCartItemById(Long id);
    void deleteCartItemById(Long id);
    CartItemResponseDto updateCartItemById(Long id, CartItemResponseDto cartItemDto);
}
