package spring.practice.elmenus_lite.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {
    private Long cartItemId;
    private Long customerId;
    private Long menuItemId;
    private Integer quantity;
}
