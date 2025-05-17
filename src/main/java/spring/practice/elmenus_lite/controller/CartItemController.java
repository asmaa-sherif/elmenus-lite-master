package spring.practice.elmenus_lite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.service.CartItemService;

@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDto> getCartItemById(@PathVariable Long id) {
        CartItemResponseDto item = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addCartItem(@RequestBody CartItemResponseDto itemDto) {
        CartItemResponseDto createdItem = cartItemService.addCartItem(itemDto);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDto> updateCartItem(@PathVariable Long id, @RequestBody CartItemResponseDto itemDto) {
        CartItemResponseDto updatedItem = cartItemService.updateCartItemById(id, itemDto);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.noContent().build();
    }
}
