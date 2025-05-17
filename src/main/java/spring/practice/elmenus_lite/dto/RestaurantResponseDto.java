package spring.practice.elmenus_lite.dto;

public class RestaurantResponseDto {
    private String restaurantName;
    private boolean active;

    public RestaurantResponseDto(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
