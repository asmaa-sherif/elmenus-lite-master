package spring.practice.elmenus_lite.service.order;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.entity.Order;
import spring.practice.elmenus_lite.entity.OrderItem;
import spring.practice.elmenus_lite.entity.User;
import spring.practice.elmenus_lite.mapper.OrderItemMapper;
import spring.practice.elmenus_lite.mapper.OrderMapper;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.ORDER_NOT_FOUND;

@Service
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public OrderDetailsDto getOrderDetails(Long orderId) {
        Order order = getOrder(orderId);
        OrderDetailsDto dto = orderMapper.toOrderDetailsDto(order);
        List<OrderItem> items = getOrderItems(order);

        dto.setCustomerName(getCustomerName(order));
        dto.setRestaurantName(getRestaurantName(items));


        getPaymentMethod(orderId).ifPresent(dto::setPaymentMethod);

        dto.setItems(items.stream().map(orderItemMapper::toOrderItemDto).toList());

        return dto;
    }

    private Optional<String> getPaymentMethod(Long orderId) {
        return transactionRepository.findByOrderOrderId(orderId).map(t -> t.getPaymentMethod().getPaymentType());
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND.getMessage() + orderId));
    }

    private static String getRestaurantName(List<OrderItem> items) {
        return items.stream().findFirst().map(item -> item.getMenuItem().getMenu().getRestaurant().getRestaurantName()).orElse(null);
    }

    private static List<OrderItem> getOrderItems(Order order) {
        return order.getItems();
    }

    private static String getCustomerName(Order order) {
        User user = order.getCustomer().getUser();
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public OrderSummaryDto getOrderSummary(Long orderId) {
        Order order = getOrder(orderId);

        OrderSummaryDto dto = orderMapper.toOrderSummaryDto(order);
        List<OrderItem> items = getOrderItems(order);

        dto.setCustomerName(getCustomerName(order));

        dto.setRestaurantName(getRestaurantName(items));

        return dto;
    }

    @Override
    public List<OrderDetailsDto> getRestaurantOrdersHistory(Long restaurantId) {
        // TODO: Implement method logic
        return null;
    }
}
