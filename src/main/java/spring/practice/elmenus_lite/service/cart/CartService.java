package spring.practice.elmenus_lite.service.cart;

import spring.practice.elmenus_lite.dto.cart.CartDto;

public interface CartService {
    Boolean deleteCart(Long id);

    CartDto getCartByCustomerId(Long customerId);
}
