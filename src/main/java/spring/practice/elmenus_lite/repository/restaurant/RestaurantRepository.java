package spring.practice.elmenus_lite.repository.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByRestaurantNameIgnoreCase(String restaurantName);

    Optional<Restaurant> findByRestaurantNameIgnoreCase(String restaurantName);

    Page<Restaurant> findAllByActiveTrue(Pageable pageable);

}

