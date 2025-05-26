package spring.practice.elmenus_lite.service.restaurant;

import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import java.util.List;

public interface RestaurantService {

    RestaurantResponseDto getRestaurantById(Long id);

    List<RestaurantResponseDto> getAllRestaurants();

    RestaurantResponseDto addRestaurant(RestaurantResponseDto restaurantDto);

    RestaurantResponseDto updateRestaurant(Long id, RestaurantResponseDto restaurantDto);

    void deleteRestaurant(Long id);
}
