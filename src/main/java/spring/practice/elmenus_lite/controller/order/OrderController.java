package spring.practice.elmenus_lite.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.service.order.OrderService;

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
    public ResponseEntity<Page<OrderSummaryDto>> getRestaurantOrdersHistory(@PathVariable @Min(value = 1, message = INVALID_ID) Long restaurantId,
                                                                            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number must be 0 or greater") int page,
                                                                            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderSummaryDto> orderSummaries = orderService.getRestaurantOrdersHistory(restaurantId, pageable);
        return ResponseEntity.ok(orderSummaries);
    }


}
