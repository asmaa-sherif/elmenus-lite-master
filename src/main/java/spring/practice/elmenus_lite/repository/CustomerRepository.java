package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserUserId(Long userId);
}
