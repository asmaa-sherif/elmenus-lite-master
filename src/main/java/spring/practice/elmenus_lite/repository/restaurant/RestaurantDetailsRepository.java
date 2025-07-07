package spring.practice.elmenus_lite.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantDetails;

@Repository
public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

    boolean existsByPhone(String phone);
}