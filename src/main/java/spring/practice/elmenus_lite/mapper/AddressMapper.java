package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.order.AddressDto;
import spring.practice.elmenus_lite.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "addressId", target = "id")
    AddressDto toAddressDto(Address address);
}
