package spring.practice.elmenus_lite.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantCategory;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "restaurantDetails", ignore = true)
    @Mapping(target = "restaurantCategories", ignore = true)
    Restaurant toEntity(RestaurantRegisterRequestDto request);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "description", source = "restaurantDetails.description")
    @Mapping(target = "phone", source = "restaurantDetails.phone")
    @Mapping(target = "estimatedDeliveryTime", ignore = true)
    @Mapping(target = "openTime", source = "restaurantDetails.openTime")
    @Mapping(target = "closeTime", source = "restaurantDetails.closeTime")
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    RestaurantResponseDto toDto(Restaurant restaurant);

    @AfterMapping
    default void mapCategoriesAndLocation(@MappingTarget RestaurantResponseDto response, Restaurant restaurant) {
        mapCategories(response, restaurant);
        mapLocationCoordinates(response, restaurant);
        mapEstimatedDeliveryTime(response, restaurant);
    }

    private void mapCategories(RestaurantResponseDto response, Restaurant restaurant) {
        if (restaurant.getRestaurantCategories() != null && !restaurant.getRestaurantCategories().isEmpty()) {
            List<RestaurantResponseDto.CategoryResponse> categories = restaurant.getRestaurantCategories().stream()
                    .map(this::mapCategoryResponse)
                    .collect(Collectors.toList());
            response.setCategories(categories);
        }
    }

    private RestaurantResponseDto.CategoryResponse mapCategoryResponse(RestaurantCategory restaurantCategory) {
        RestaurantResponseDto.CategoryResponse categoryResponse = new RestaurantResponseDto.CategoryResponse();
        categoryResponse.setCategoryId(restaurantCategory.getCategory().getCategoryId());
        categoryResponse.setCategoryName(restaurantCategory.getCategory().getCategoryName());
        return categoryResponse;
    }

    private void mapLocationCoordinates(RestaurantResponseDto response, Restaurant restaurant) {
        if (restaurant.getRestaurantDetails() != null && restaurant.getRestaurantDetails().getLocation() != null) {
            response.setLatitude(restaurant.getRestaurantDetails().getLocation().getY());
            response.setLongitude(restaurant.getRestaurantDetails().getLocation().getX());
        }
    }

    private void mapEstimatedDeliveryTime(RestaurantResponseDto response, Restaurant restaurant) {
        if (restaurant.getRestaurantDetails() != null && restaurant.getRestaurantDetails().getEstimatedDeliveryTime() != null) {
            String intervalStr = restaurant.getRestaurantDetails().getEstimatedDeliveryTime().toString();
            response.setEstimatedDeliveryTime(formatIntervalToString(intervalStr));
        }
    }

    default String formatIntervalToString(String intervalStr) {
        if (intervalStr != null && intervalStr.startsWith("PT")) {
            if (intervalStr.contains("H") && intervalStr.contains("M")) {
                String hours = intervalStr.substring(2, intervalStr.indexOf("H"));
                String minutes = intervalStr.substring(intervalStr.indexOf("H") + 1, intervalStr.indexOf("M"));
                return hours + " hours " + minutes + " minutes";
            } else if (intervalStr.contains("H")) {
                String hours = intervalStr.substring(2, intervalStr.indexOf("H"));
                return hours + " hour" + (hours.equals("1") ? "" : "s");
            } else if (intervalStr.contains("M")) {
                String minutes = intervalStr.substring(2, intervalStr.indexOf("M"));
                return minutes + " minute" + (minutes.equals("1") ? "" : "s");
            }
        }
        return intervalStr;
    }
}
