package spring.practice.elmenus_lite.controller.cart;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.BaseResponse;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.service.cart.CartService;

import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_CART_ID;
import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_CUSTOMER_ID;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CART_CLEARED_SUCCESSFULLY;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CART_RETRIEVED_SUCCESSFULLY;


@RestController
@RequestMapping("/api/v1/cart")
@Validated
@Tag(name = "Cart Management", description = "Endpoints for managing customer carts, including retrieval and deletion operations")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<BaseResponse<CartDto>> deleteCart(@PathVariable @Min(value = 1, message = INVALID_CART_ID) Long cartId) {
        try {
            cartService.deleteCart(cartId);
            return ResponseEntity.ok(new BaseResponse<>(true, CART_CLEARED_SUCCESSFULLY.getMessage(), null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<BaseResponse<CartDto>> getCartByCustomerId(@PathVariable @Min(value = 1, message = INVALID_CUSTOMER_ID) Long customerId) {
        try {
            CartDto cart = cartService.getCartByCustomerId(customerId);
            return ResponseEntity.ok().body(new BaseResponse<>(true, CART_RETRIEVED_SUCCESSFULLY.getMessage(), cart));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        }
    }
}
