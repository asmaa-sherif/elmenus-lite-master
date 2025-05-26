package spring.practice.elmenus_lite.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.mapper.OrderMapper;
import spring.practice.elmenus_lite.repository.OrderRepository;

import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) {
        // TODO: Implement method logic
        return null;
    }

    @Override
    public OrderSummaryDto getOrderSummary(Long orderId) {
        // TODO: Implement method logic
        return null;
    }

    @Override
    public List<OrderDetailsDto> getRestaurantOrdersHistory(Long restaurantId) {
        // TODO: Implement method logic
        return null;
    }
}
