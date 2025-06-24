package spring.practice.elmenus_lite.entity.restaurant;

import jakarta.persistence.*;
import lombok.*;
import spring.practice.elmenus_lite.entity.AbstractEntity;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String categoryName;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RestaurantCategory> restaurantCategories = new HashSet<>();
}
