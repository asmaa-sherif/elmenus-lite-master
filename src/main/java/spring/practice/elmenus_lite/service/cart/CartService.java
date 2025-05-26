package spring.practice.elmenus_lite.service.cart;

import spring.practice.elmenus_lite.dto.cart.CartDto;

import java.util.List;

public interface CartService {
    CartDto getCartById(Long id);

    CartDto addCart(CartDto cartDto);

    CartDto updateCart(Long id, CartDto cartDto);

    Boolean deleteCart(Long id);

    List<CartDto> getAllCarts();

    CartDto getCartByCustomerId(Long customerId);
}
