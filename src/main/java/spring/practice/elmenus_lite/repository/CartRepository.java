package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // You can add custom queries here if needed

    // return all carts that related to this customer ID
    List<Cart> findByCustomerCustomerId(Long customerId);
}
