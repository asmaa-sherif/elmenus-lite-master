package spring.practice.elmenus_lite.service.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;


public interface OrderService {

    OrderDetailsDto getOrderDetails(Long orderId);

    OrderSummaryDto getOrderSummary(Long orderId);

    Page<OrderSummaryDto> getRestaurantOrdersHistory(Long restaurantId, Pageable pageable);

}
