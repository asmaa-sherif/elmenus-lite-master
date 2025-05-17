package spring.practice.elmenus_lite.dto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartResponseDto {
    private Long cartId;
    private Long customerId;
    private String customerName;
    private List<CartItemResponseDto> items;
    private Double grandTotal;
}
