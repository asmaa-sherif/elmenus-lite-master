package spring.practice.elmenus_lite.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantDetails;

import java.util.Optional;

@Repository
public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

    @Query("SELECT rd FROM RestaurantDetails rd WHERE rd.restaurant.restaurantId = :restaurantId")
    Optional<RestaurantDetails> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT rd FROM RestaurantDetails rd WHERE rd.phone = :phone")
    Optional<RestaurantDetails> findByPhone(@Param("phone") String phone);

    boolean existsByPhone(String phone);
}