package spring.practice.elmenus_lite.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.BaseResponse;
import spring.practice.elmenus_lite.dto.CartDto;
import spring.practice.elmenus_lite.service.CartService;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;

@RestController
@RequestMapping("/api/v1/cart")
@Tag(name = "Cart Management", description = "Endpoints for managing customer carts, including retrieval and deletion operations")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<CartDto>> deleteCart(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_CART_ID.getMessage(), null));
        }
        try {
            cartService.deleteCart(id);
            return ResponseEntity.ok(new BaseResponse<>(true, CART_CLEARED_SUCCESSFULLY.getMessage(), null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<BaseResponse<CartDto>> getCartByCustomerId(@PathVariable Long customerId) {
        if (customerId <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_CUSTOMER_ID.getMessage(), null));
        }
        try {
            CartDto cart = cartService.getCartByCustomerId(customerId);
            return ResponseEntity.ok().body(new BaseResponse<>(true, CART_RETRIEVED_SUCCESSFULLY.getMessage(), cart));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        }
    }
}
