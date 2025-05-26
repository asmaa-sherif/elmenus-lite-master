package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemRequestDto;
import spring.practice.elmenus_lite.entity.CartItem;

//add, delete , update
public interface CartItemService {
    void addCartItem(CartItemRequestDto cartItemRequestDto);

    CartItem getCartItemById(Long id);

    void deleteCartItemById(Long id);

    CartItemDto updateCartItemById(CartItemRequestDto cartItemRequestDto);

    CartItemDto updateCartItemQuantity(CartItemRequestDto cartItemRequest);
}
