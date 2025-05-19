package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartDto;

import java.util.List;

public interface CartService {
    CartDto getCartById(Long id);

    CartDto addCart(CartDto cartDto);

    CartDto updateCart(Long id, CartDto cartDto);

    Boolean deleteCart(Long id);

    List<CartDto> getAllCarts();

    CartDto getCartByCustomerId(Long customerId);
}
