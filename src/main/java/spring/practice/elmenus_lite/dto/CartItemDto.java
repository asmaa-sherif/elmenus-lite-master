package spring.practice.elmenus_lite.dto;

import lombok.*;
import spring.practice.elmenus_lite.entity.MenuItem;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItemDto {
    private Long cartItemId;
    private MenuItemDto menuItem;
    private String productName;
    private double price;
    private int quantity;
    private double total;


}
