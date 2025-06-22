package spring.practice.elmenus_lite.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.entity.Restaurant;
import spring.practice.elmenus_lite.mapper.RestaurantMapper;
import spring.practice.elmenus_lite.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository , RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;


    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id){
        return null;
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants(){
        return null;
    }

    @Override
    public RestaurantResponseDto addRestaurant(RestaurantRequestDto restaurantDto){
        Optional<Restaurant> isExist = restaurantRepository.findByRestaurantName(restaurantDto.getName());
        if(isExist.isPresent()){
            throw new RuntimeException("Restaurant already exists!");
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantDto.getName());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();
        restaurantResponseDto.setRestaurantName(savedRestaurant.getRestaurantName());
        restaurantResponseDto.setRestaurantId(savedRestaurant.getRestaurantId());
        return restaurantResponseDto;
    }


    @Override
    public RestaurantResponseDto updateRestaurant(Long id, RestaurantResponseDto restaurantDto){
        return null;
    }

    @Override
    public void deleteRestaurant(Long id){
        return;
    }






}
