package spring.practice.elmenus_lite.controller.restaurant;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.service.restaurant.RestaurantService;

@Log4j2
@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurants Management", description = "Endpoints for managing restaurants, including registering, ")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/register")
    public ResponseEntity<RestaurantResponseDto> register(@RequestBody @Valid RestaurantRegisterRequestDto restaurantRegisterRequestDto) {
        log.info("Received restaurant registration request for: {}", restaurantRegisterRequestDto.getRestaurantName());
        RestaurantResponseDto response = restaurantService.registerRestaurant(restaurantRegisterRequestDto);
        return ResponseEntity.ok(response);
    }
}
