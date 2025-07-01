package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
