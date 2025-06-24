package spring.practice.elmenus_lite.repository.restaurant;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.entity.restaurant.Category;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.categoryId IN :ids")
    List<Category> findAllByIdIn(@Param("ids") Set<Long> ids);

    boolean existsByCategoryId(Long categoryId);
}
