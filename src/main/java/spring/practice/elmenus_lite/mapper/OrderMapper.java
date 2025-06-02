package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface OrderMapper {
    @Mapping(target = "status", expression = "java(order.getOrderStatus().getOrderStatusName())")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "items", ignore = true)               // Set manually in service
    @Mapping(target = "customerName", ignore = true)        // Set manually in service
    @Mapping(target = "restaurantName", ignore = true)      // Set manually in service
    @Mapping(target = "paymentMethod", ignore = true)
    OrderDetailsDto toOrderDetailsDto(Order order);


    @Mapping(target = "status", expression = "java(order.getOrderStatus().getOrderStatusName())")
    @Mapping(target = "customerName", ignore = true)        // Set manually in service
    @Mapping(target = "restaurantName", ignore = true)
        // Set manually in service
    OrderSummaryDto toOrderSummaryDto(Order order);

    List<OrderDetailsDto> toOrderDetailsDtoList(List<Order> orders);
}
