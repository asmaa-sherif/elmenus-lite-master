package spring.practice.elmenus_lite.controller.cart;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.BaseResponse;
import spring.practice.elmenus_lite.dto.cart.CartItemDto;
import spring.practice.elmenus_lite.dto.cart.CartItemRequestDto;
import spring.practice.elmenus_lite.dto.cart.UpdateItemQuantityRequest;
import spring.practice.elmenus_lite.service.cart.CartItemService;

import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_CART_ITEM_ID;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;


@RestController
@RequestMapping("/api/v1/cartItem")
@Validated
@Tag(name = "Cart Item Management", description = "Endpoints for managing customer carts, including adding, viewing, updating, and deleting cart items")
public class CartItemController {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CartItemDto>> addCartItem(@Valid @RequestBody CartItemRequestDto cartItemRequestDto) {

        cartItemService.addCartItem(cartItemRequestDto);
        return ResponseEntity.ok(new BaseResponse<>(true, CART_ITEM_ADDED_SUCCESSFULLY.getMessage(), null));

    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<BaseResponse<CartItemDto>> deleteCartItem(@PathVariable @Min(value = 1, message = INVALID_CART_ITEM_ID) Long cartItemId) {
        cartItemService.deleteCartItemById(cartItemId);
        return ResponseEntity.ok(new BaseResponse<>(true, CART_ITEM_DELETED_SUCCESSFULLY.getMessage(), null));

    }

    @PatchMapping("/updateItemQuantity")
    public ResponseEntity<BaseResponse<CartItemDto>> updateItemQuantity(@Valid @RequestBody UpdateItemQuantityRequest updateItemQuantityRequest) {
        CartItemDto updatedItem = cartItemService.updateCartItemQuantity(updateItemQuantityRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(true, QUANTITY_UPDATED_SUCCESSFULLY.getMessage(), updatedItem));

    }
}
