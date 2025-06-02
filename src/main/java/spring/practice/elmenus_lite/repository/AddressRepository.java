package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
