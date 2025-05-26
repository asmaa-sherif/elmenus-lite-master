package spring.practice.elmenus_lite.dto.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import spring.practice.elmenus_lite.dto.menu.MenuItemDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItemDto {
    private Long cartItemId;
    private MenuItemDto menuItem;
    private int quantity;
    private double total;
}
