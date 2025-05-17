package spring.practice.elmenus_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long id) {
        CartResponseDto cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getAllCarts() {
        List<CartResponseDto> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @PostMapping
    public ResponseEntity<CartResponseDto> addCart(@RequestBody CartResponseDto cartDto) {
        CartResponseDto createdCart = cartService.addCart(cartDto);
        return ResponseEntity.ok(createdCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDto> updateCart(@PathVariable Long id, @RequestBody CartResponseDto cartDto) {
        CartResponseDto updatedCart = cartService.updateCart(id, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
