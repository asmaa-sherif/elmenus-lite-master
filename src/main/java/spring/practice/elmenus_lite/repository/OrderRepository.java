package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findByCustomerCustomerId(Long customerId);
}

