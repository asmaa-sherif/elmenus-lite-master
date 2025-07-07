package spring.practice.elmenus_lite.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RestaurantRegisterRequestDto {
    @NotBlank(message = "Restaurant name is required")
    @NotNull(message = "Restaurant name is required")
    private String restaurantName;

    private boolean active = true;

    @Size(min = 1, message = "Minimum 1 category allowed")
    private List<Long> categoryIds;

    // RestaurantDetails fields
    @NotNull(message = "description is required")
    private String description;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    //Todo check format
    @NotNull(message = "estimatedDeliveryTime is required")
    @Pattern(regexp = "^\\d{1,3}\\s*(min|minutes|hour|hours)$", message = "Invalid delivery time format (e.g., '30 min', '2 hours')")
    private String estimatedDeliveryTime;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$", message = "Invalid time format (HH:MM or HH:MM:SS)")
    private String openTime;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$", message = "Invalid time format (HH:MM or HH:MM:SS)")
    private String closeTime;


    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;
}