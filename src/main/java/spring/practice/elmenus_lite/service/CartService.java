package spring.practice.elmenus_lite.service;

import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.repository.CartRepository;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResponseDto getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItemResponseDto> items = cart.getCartItems().stream().map(item -> {
            CartItemResponseDto dto = new CartItemResponseDto();
            dto.setProductName(item.getMenuItem().getMenuName()); // Assuming menuName exists
            dto.setPrice(item.getMenuItem().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setTotal(item.getQuantity() * item.getMenuItem().getPrice());
            return dto;
        }).toList();

        double total = items.stream().mapToDouble(CartItemResponseDto::getTotal).sum();

        CartResponseDto response = new CartResponseDto();
        response.setCustomerId(cart.getCustomer().getCustomerId());
        response.setCustomerName(cart.getCustomer().getFullName());

        return response;
    }
}
