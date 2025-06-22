package spring.practice.elmenus_lite.dto.restaurant;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestaurantResponseDto {
    private Long restaurantId;
    private String restaurantName;
    private boolean active;
}
