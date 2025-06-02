package spring.practice.elmenus_lite.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderSummaryDto {
    private Long orderId;
    private String status;
    private String restaurantName;
    private String customerName;
    private LocalDateTime orderDate;
    private Double total;
}

