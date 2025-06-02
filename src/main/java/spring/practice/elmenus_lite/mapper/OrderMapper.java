package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.order.AddressDto;
import spring.practice.elmenus_lite.dto.order.OrderDetailsDto;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.dto.order.PlaceOrderRequestDto;
import spring.practice.elmenus_lite.entity.Address;
import spring.practice.elmenus_lite.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

        OrderSummaryDto toSummaryDto(Order order);

        Order toEntity(PlaceOrderRequestDto placeOrderRequestDto);
        OrderDetailsDto toDetailsDto(Order order);

        AddressDto map(Address address);
        Address map(AddressDto addressDto);


}
