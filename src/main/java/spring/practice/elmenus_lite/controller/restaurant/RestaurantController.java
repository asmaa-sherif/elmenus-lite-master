package spring.practice.elmenus_lite.controller.restaurant;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.BaseResponse;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.service.restaurant.RestaurantService;

import static spring.practice.elmenus_lite.common.ValidationMessages.INVALID_ID;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/restaurants")
@Validated
@Tag(name = "Restaurants Management", description = "Endpoints for managing restaurants, including registering, ")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RestaurantResponseDto>> register(@RequestBody @Valid RestaurantRegisterRequestDto restaurantRegisterRequestDto) {
        log.info("Received restaurant registration request for: {}", restaurantRegisterRequestDto.getRestaurantName());
        RestaurantResponseDto response = restaurantService.registerRestaurant(restaurantRegisterRequestDto);
        return ResponseEntity.ok(new BaseResponse<>(true, ADDED_SUCCESSFULLY.getMessage(), response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<RestaurantResponseDto>> updateRestaurant(@PathVariable @Min(value = 1, message = INVALID_ID) Long id,
                                                                                @Valid @RequestBody RestaurantRegisterRequestDto restaurantRegisterRequestDto) {
        log.info("Received restaurant update request for: {}", restaurantRegisterRequestDto.getRestaurantName());
        RestaurantResponseDto response = restaurantService.updateRestaurant(id, restaurantRegisterRequestDto);
        return ResponseEntity.ok(new BaseResponse<>(true, UPDATED_SUCCESSFULLY.getMessage(), response));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BaseResponse<Void>> toggleStatus(@PathVariable @Min(value = 1, message = INVALID_ID) Long id, @RequestParam boolean isActive) {
        restaurantService.setActiveStatus(id, isActive);
        return ResponseEntity.ok(new BaseResponse<>(true, UPDATED_SUCCESSFULLY.getMessage(), null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<RestaurantResponseDto>>> getRestaurants(Pageable pageable) {
        log.info("Received restaurant list request: {}", pageable);

        Page<RestaurantResponseDto> list = restaurantService.viewAllRestaurants(pageable);
        return ResponseEntity.ok(new BaseResponse<>(true, RETRIEVED_SUCCESSFULLY.getMessage(), list));
    }


}
