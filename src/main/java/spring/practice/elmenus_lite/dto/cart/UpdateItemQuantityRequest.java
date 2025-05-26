package spring.practice.elmenus_lite.dto.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_CART_ITEM_ID;
import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_QUANTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemQuantityRequest {
    @NotNull(message = INVALID_CART_ITEM_ID)
    @Min(value = 1, message = INVALID_CART_ITEM_ID)
    private Long cartItemId;

    @NotNull(message = INVALID_QUANTITY)
    @Min(value = 1, message = INVALID_QUANTITY)
    private Integer quantity;
}
