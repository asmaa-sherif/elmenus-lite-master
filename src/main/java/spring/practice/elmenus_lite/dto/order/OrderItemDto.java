package spring.practice.elmenus_lite.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemDto {
    private String id;
    private String name;
    private Integer quantity;
    private Double price;
}
