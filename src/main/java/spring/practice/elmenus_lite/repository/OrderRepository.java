package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
