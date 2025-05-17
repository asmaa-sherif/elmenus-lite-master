package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartResponseDto;

import java.util.List;

public interface CartService {
    CartResponseDto getCartById(Long id);
    CartResponseDto addCart(CartResponseDto cartDto);
    CartResponseDto updateCart(Long id, CartResponseDto cartDto);
    void deleteCart(Long id);
    List<CartResponseDto> getAllCarts();
}
