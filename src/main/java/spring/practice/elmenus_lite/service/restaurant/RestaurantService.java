package spring.practice.elmenus_lite.service.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;

import java.util.List;

public interface RestaurantService {

    RestaurantResponseDto registerRestaurant(RestaurantRegisterRequestDto request);

    RestaurantResponseDto updateRestaurant(Long id, RestaurantRegisterRequestDto request);

    void setActiveStatus(Long id, boolean isActive);

    Page<RestaurantResponseDto> viewAllRestaurants(Pageable pageable);

    Page<RestaurantResponseDto> searchRestaurants(String keyword, Pageable pageable);

    List<RestaurantResponseDto> getTopRestaurants(int limit);

    List<RestaurantResponseDto> getRecommendedRestaurants(Long customerId, int limit);
}
