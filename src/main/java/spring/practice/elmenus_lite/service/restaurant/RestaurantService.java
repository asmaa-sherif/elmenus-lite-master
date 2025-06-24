package spring.practice.elmenus_lite.service.restaurant;

import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;

public interface RestaurantService {

    RestaurantResponseDto registerRestaurant(RestaurantRegisterRequestDto request);

    RestaurantResponseDto updateRestaurant(Long id, RestaurantRegisterRequestDto request);

    void setActiveStatus(Long id, boolean isActive);
}
