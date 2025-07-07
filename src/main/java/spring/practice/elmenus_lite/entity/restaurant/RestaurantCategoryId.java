package spring.practice.elmenus_lite.entity.restaurant;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RestaurantCategoryId implements Serializable {
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "category_id")
    private Long categoryId;
}

