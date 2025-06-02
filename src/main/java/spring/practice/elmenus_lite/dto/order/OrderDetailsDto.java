package spring.practice.elmenus_lite.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetailsDto {
    private Long orderId;
    private String customerName;
    private AddressDto address;
    private String status;
    private String restaurantName;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
    private Double subtotal;
    private Double discountAmount;
    private Double total;
    private String paymentMethod;
}
