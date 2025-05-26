package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CartDto;

public interface CartService {
    Boolean deleteCart(Long id);

    CartDto getCartByCustomerId(Long customerId);
}
