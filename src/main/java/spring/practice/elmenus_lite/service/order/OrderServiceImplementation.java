package spring.practice.elmenus_lite.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.dto.order.PlaceOrderRequestDto;
import spring.practice.elmenus_lite.entity.Order;
import spring.practice.elmenus_lite.entity.OrderStatus;
import spring.practice.elmenus_lite.enums.OrderStatusType;
import spring.practice.elmenus_lite.mapper.OrderMapper;
import spring.practice.elmenus_lite.repository.AddressRepository;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;


    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository, OrderMapper orderMapper, OrderStatusRepository orderStatusRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderStatusRepository = orderStatusRepository;
        this.addressRepository = addressRepository;

    }

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) {
        // TODO: Implement method logic
        return null;
    }

    @Override
    public OrderSummaryDto getOrderSummary(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toSummaryDto(order);
    }

    @Override
    public List<OrderDetailsDto> getRestaurantOrdersHistory(Long restaurantId) {
        // TODO: Implement method logic
        return null;
    }

    @Override
    public OrderSummaryDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        Order order = orderMapper.toEntity(placeOrderRequestDto);

        /*OrderStatus status = orderStatusRepository
                .findByOrderStatusName("PENDING")
                .orElseThrow(() -> new RuntimeException("Status not found"));
        order.setOrderStatus(status);
         */

        order.setOrderDate(LocalDateTime.now());

        if (order.getAddress() != null) {
            addressRepository.save(order.getAddress());
        }

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toSummaryDto(savedOrder);
        }

    }



