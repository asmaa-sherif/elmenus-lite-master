package spring.practice.elmenus_lite.dto;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartDto {
    private Long cartId;
    private Long customerId;
    private String customerName;
    private List<CartItemDto> cartItems;
    private Double grandTotal;
}
