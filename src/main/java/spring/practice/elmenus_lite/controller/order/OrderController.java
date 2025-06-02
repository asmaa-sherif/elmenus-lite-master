package spring.practice.elmenus_lite.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.dto.order.PlaceOrderRequestDto;
import spring.practice.elmenus_lite.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order Management", description = "Endpoints for managing customer orders, including adding, viewing, updating, and cancelling")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping("/{orderId}/summary")
    public ResponseEntity<OrderSummaryDto> getOrderSummary(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderSummary(orderId));
    }

    @GetMapping("/restaurant/{restaurantId}/history")
    public ResponseEntity<List<OrderDetailsDto>> getRestaurantOrdersHistory(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getRestaurantOrdersHistory(restaurantId));
    }

    @PostMapping("/place")
    public ResponseEntity<OrderSummaryDto> placeOrder(@RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        OrderSummaryDto orderSummary = orderService.placeOrder(placeOrderRequestDto);
        return ResponseEntity.ok(orderSummary);
    }


}
