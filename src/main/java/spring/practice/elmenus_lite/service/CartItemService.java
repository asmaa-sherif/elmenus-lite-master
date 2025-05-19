package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartItemDto;

//add, delete , update
public interface CartItemService {
    void addCartItem(Long customerId, Long menuItemId, Integer quantity);

    CartItemDto getCartItemById(Long id);

    Boolean deleteCartItemById(Long id);

    CartItemDto updateCartItemById(Long id, CartItemDto cartItemDto);

    CartItemDto updateCartItemQuantity(Long cartId, Long cartItemId, Integer quantity);
}
