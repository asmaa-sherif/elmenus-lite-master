package spring.practice.elmenus_lite.dto.cart;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static spring.practice.elmenus_lite.common.ValidationMessages.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {
    private Long cartItemId;

    @NotNull(message = INVALID_CUSTOMER_ID)
    @Min(value = 1, message = INVALID_CUSTOMER_ID)
    private Long customerId;

    @NotNull(message = INVALID_MENU_ITEM_ID)
    @Min(value = 1, message = INVALID_MENU_ITEM_ID)
    private Long menuItemId;

    @NotNull(message = INVALID_QUANTITY)
    @Min(value = 1, message = INVALID_QUANTITY)
    private Integer quantity;
}
