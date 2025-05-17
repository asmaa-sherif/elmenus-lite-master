package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseDto {
    private Long menuItemId;
    private String productName;
    private double price;
    private int quantity;
    private double total;


}
