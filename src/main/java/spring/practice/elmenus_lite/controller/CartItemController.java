package spring.practice.elmenus_lite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.BaseResponse;
import spring.practice.elmenus_lite.dto.CartDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.UpdateItemQuantityRequestBody;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.CartItemService;

@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable Long id) {
        CartItemDto item = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto itemDto) {
        CartItemDto createdItem = cartItemService.addCartItem(itemDto);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long id, @RequestBody CartItemDto itemDto) {
        CartItemDto updatedItem = cartItemService.updateCartItemById(id, itemDto);
        return ResponseEntity.ok(updatedItem);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<CartItemDto>> deleteCartItem(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, "Invalid Cart Item ID", null));
        }
        try {
            cartItemService.deleteCartItemById(id);
            return ResponseEntity.ok(new BaseResponse<>(true, "Cart item deleted successfully", null));
        } catch (NotFoundCustomException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(false, ex.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(false, e.getMessage(), null));
        }
    }

    @PatchMapping("/{cartId}/{cartItemId}/quantity")
    public ResponseEntity<BaseResponse<CartItemDto>> updateItemQuantity(@PathVariable("cartId") Long cartId,
                                                                        @PathVariable("cartItemId") Long cartItemId,
                                                                        @RequestBody UpdateItemQuantityRequestBody quantity) {
        if (quantity == null || quantity.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, "Invalid quantity", null));
        }

        if (cartItemId == null || cartItemId <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, "Invalid cart item ID", null));
        }

        if (cartId == null || cartId <= 0) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, "Invalid cart ID", null));
        }

        CartItemDto updatedItem = cartItemService.updateCartItemQuantity(cartId, cartItemId, quantity.getQuantity());

        if (updatedItem == null || updatedItem.getCartItemId() == null) {
            return ResponseEntity.internalServerError().body(new BaseResponse<>(false, "can not update quantity", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(true, "Quantity updated successfully", updatedItem));

    }
}
