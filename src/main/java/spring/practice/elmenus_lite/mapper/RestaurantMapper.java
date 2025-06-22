package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.entity.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant toEntity (RestaurantRequestDto restaurantRequestDto);
    RestaurantResponseDto toDto (Restaurant restaurant);
}
