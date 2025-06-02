package spring.practice.elmenus_lite.dto.order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlaceOrderItemDto {
    private Long menuItemId;
    private Integer quantity;
}
