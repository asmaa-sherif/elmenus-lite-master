package spring.practice.elmenus_lite.mapper;

import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import spring.practice.elmenus_lite.dto.order.AddressDto;
import spring.practice.elmenus_lite.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "addressId", target = "id")
    @Mapping(target = "location", source = "location", qualifiedByName = "pointToLatLngString")
//    @Mapping(target = "location", ignore = true)
    AddressDto toAddressDto(Address address);

    @Named("pointToLatLngString")
    static String pointToLatLngString(Point point) {
        if (point == null) return null;
        return point.getY() + "," + point.getX(); // lat,lon
    }
}
