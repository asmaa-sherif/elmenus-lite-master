package spring.practice.elmenus_lite.service.order;

import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.dto.order.PlaceOrderRequestDto;

import java.util.List;


public interface OrderService {

    OrderDetailsDto getOrderDetails(Long orderId);

    OrderSummaryDto getOrderSummary(Long orderId);

    List<OrderDetailsDto> getRestaurantOrdersHistory(Long restaurantId);

    OrderSummaryDto placeOrder(PlaceOrderRequestDto request);

    }
