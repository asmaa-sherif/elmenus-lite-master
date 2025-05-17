package spring.practice.elmenus_lite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public CartResponseDto getCart(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    // You can later add endpoints for addItem, removeItem, etc.
}
