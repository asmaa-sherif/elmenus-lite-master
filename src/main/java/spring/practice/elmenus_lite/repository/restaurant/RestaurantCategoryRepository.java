package spring.practice.elmenus_lite.repository.restaurant;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantCategory;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantCategoryId;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, RestaurantCategoryId> {
    void deleteByRestaurant(Restaurant restaurant);
}

