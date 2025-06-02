package spring.practice.elmenus_lite.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.service.order.OrderService;

import java.util.List;

import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_ID;

@RestController
@RequestMapping("/api/v1/order")
@Validated
@Tag(name = "Order Management", description = "Endpoints for managing customer orders, including adding, viewing, updating, and cancelling")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable @Min(value = 1, message = INVALID_ID) Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping("/{orderId}/summary")
    public ResponseEntity<OrderSummaryDto> getOrderSummary(@PathVariable @Min(value = 1, message = INVALID_ID) Long orderId) {
        return ResponseEntity.ok(orderService.getOrderSummary(orderId));
    }

    @GetMapping("/restaurant/{restaurantId}/history")
    public ResponseEntity<List<OrderDetailsDto>> getRestaurantOrdersHistory(@PathVariable @Min(value = 1, message = INVALID_ID) Long restaurantId) {
        return ResponseEntity.ok(orderService.getRestaurantOrdersHistory(restaurantId));
    }


}
