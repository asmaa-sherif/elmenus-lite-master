package spring.practice.elmenus_lite.service.cart;

import spring.practice.elmenus_lite.dto.cart.CartItemDto;
import spring.practice.elmenus_lite.dto.cart.CartItemRequestDto;
import spring.practice.elmenus_lite.dto.cart.UpdateItemQuantityRequest;
import spring.practice.elmenus_lite.entity.CartItem;

//add, delete , update
public interface CartItemService {
    void addCartItem(CartItemRequestDto cartItemRequestDto);

    CartItem getCartItemById(Long id);

    void deleteCartItemById(Long id);

    CartItemDto updateCartItemQuantity(UpdateItemQuantityRequest updateItemQuantityRequest);
}
