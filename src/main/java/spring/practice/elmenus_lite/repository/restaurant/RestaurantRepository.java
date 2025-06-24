package spring.practice.elmenus_lite.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByRestaurantNameIgnoreCase(String restaurantName);

    Optional<Restaurant> findByRestaurantNameIgnoreCase(String restaurantName);

    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.restaurantDetails " +
            "LEFT JOIN FETCH r.restaurantCategories rc " +
            "LEFT JOIN FETCH rc.category " +
            "WHERE r.restaurantId = :id")
    Optional<Restaurant> findByIdWithDetailsAndCategories(@Param("id") Long id);

    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.restaurantDetails " +
            "LEFT JOIN FETCH r.restaurantCategories rc " +
            "LEFT JOIN FETCH rc.category " +
            "WHERE r.restaurantName = :name")
    Optional<Restaurant> findByNameWithDetailsAndCategories(@Param("name") String name);
}

