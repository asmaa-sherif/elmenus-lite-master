package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.order.OrderItemDto;
import spring.practice.elmenus_lite.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "orderItemId", target = "id")
    @Mapping(source = "menuItem.itemName", target = "name")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "unitPrice", target = "price")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
