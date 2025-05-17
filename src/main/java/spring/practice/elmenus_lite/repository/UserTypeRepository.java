package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    UserType findByUserTypeName(String userTypeName);
}
