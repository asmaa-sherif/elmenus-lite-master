package spring.practice.elmenus_lite.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.*;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.handlerException.SaveOperationException;
import spring.practice.elmenus_lite.service.CartItemService;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;

@RestController
@RequestMapping("/api/v1/cartItem")
@Tag(name = "Cart Item Management", description = "Endpoints for managing customer carts, including adding, viewing, updating, and deleting cart items")
public class CartItemController {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CartItemDto>> addCartItem(@Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        // TODO: ControllerAdvice for exceptions
        // TODO: Validation for request
        if (cartItemRequestDto.getCustomerId() == null || cartItemRequestDto.getCustomerId() <= 0 ||
                cartItemRequestDto.getMenuItemId() == null || cartItemRequestDto.getMenuItemId() <= 0 ||
                cartItemRequestDto.getQuantity() == null || cartItemRequestDto.getQuantity() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(false, INVALID_REQUEST.getMessage(), null));
        }

        try {
            cartItemService.addCartItem(cartItemRequestDto);
            return ResponseEntity.ok(new BaseResponse<>(true, CART_ITEM_ADDED_SUCCESSFULLY.getMessage(), null));
        } catch (NotFoundCustomException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(false, ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(false, e.getMessage(), null));
        }

        }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<BaseResponse<CartItemDto>> deleteCartItem(@PathVariable Long cartItemId) {
        if (cartItemId <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_CART_ITEM_ID.getMessage(), null));
        }
        try {
            cartItemService.deleteCartItemById(cartItemId);
            return ResponseEntity.ok(new BaseResponse<>(true, CART_ITEM_DELETED_SUCCESSFULLY.getMessage(), null));
        } catch (NotFoundCustomException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, e.getMessage(), null));
        }
    }

    @PatchMapping("/updateItemQuantity")
    public ResponseEntity<BaseResponse<CartItemDto>> updateItemQuantity(@RequestBody CartItemRequestDto cartItemRequest) {
        if (cartItemRequest == null) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_REQUEST.getMessage(), null));
        }

        if (cartItemRequest.getQuantity() == null || cartItemRequest.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_QUANTITY.getMessage(), null));
        }

        if (cartItemRequest.getCartItemId() == null || cartItemRequest.getCartItemId() <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, INVALID_CART_ITEM_ID.getMessage(), null));
        }

        try {
            CartItemDto updatedItem = cartItemService.updateCartItemQuantity(cartItemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(true, QUANTITY_UPDATED_SUCCESSFULLY.getMessage(), updatedItem));
        } catch (EntityNotFoundException efx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, efx.getMessage(), null));
        } catch (SaveOperationException sox){
            return ResponseEntity.internalServerError().body(new BaseResponse<>(false, sox.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, e.getMessage(), null));
        }
    }
}
