package spring.practice.elmenus_lite.dto.restaurant;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class RestaurantResponseDto {
    private Long restaurantId;
    private String restaurantName;
    private boolean active;
    private List<CategoryResponse> categories;
    private String description;
    private String phone;
    private String estimatedDeliveryTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Double latitude;
    private Double longitude;

    @Data
    public static class CategoryResponse {
        private Long categoryId;
        private String categoryName;
    }
}
