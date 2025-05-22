package spring.practice.elmenus_lite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
