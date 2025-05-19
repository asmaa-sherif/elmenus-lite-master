package spring.practice.elmenus_lite.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItemDto {
    private Long cartItemId;
    private Long menuItemId;
    private String productName;
    private double price;
    private int quantity;
    private double total;


}
