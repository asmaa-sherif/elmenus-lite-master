package spring.practice.elmenus_lite.entity.restaurant;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "restaurant_category", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "category_id"}))
public class RestaurantCategory {

    @EmbeddedId
    private RestaurantCategoryId id;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public RestaurantCategory(Restaurant restaurant, Category category) {
        this.restaurant = restaurant;
        this.category = category;
        this.id = new RestaurantCategoryId(restaurant.getRestaurantId(), category.getCategoryId());
    }
}


