package spring.practice.elmenus_lite.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlaceOrderRequestDto {
        private Long userId;
        private Long restaurantId;
        private Long promotionId;
        private AddressDto address;
        private List<OrderItemDto> items;
        private Long paymentMethodId;
}

