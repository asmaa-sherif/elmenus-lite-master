package spring.practice.elmenus_lite.repository.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByRestaurantNameIgnoreCase(String restaurantName);

    Optional<Restaurant> findByRestaurantNameIgnoreCase(String restaurantName);

    Page<Restaurant> findAllByActiveTrue(Pageable pageable);

    @Query("""
                SELECT DISTINCT r FROM Restaurant r
                LEFT JOIN r.restaurantCategories rc
                LEFT JOIN rc.category c
                WHERE LOWER(r.restaurantName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Restaurant> searchByRestaurantNameOrCategoryName(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
                SELECT r FROM Restaurant r
                JOIN r.reviews v
                WHERE r.active = true
                GROUP BY r
                ORDER BY AVG(v.rating) DESC
            """)
    List<Restaurant> findTopRatedActiveRestaurants(Pageable pageable);


}

