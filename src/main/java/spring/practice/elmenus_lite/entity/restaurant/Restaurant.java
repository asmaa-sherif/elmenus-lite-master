package spring.practice.elmenus_lite.entity.restaurant;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.entity.AbstractEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "restaurant_name", nullable = false, unique = true)
    private String restaurantName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToOne(mappedBy = "restaurant")
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantCategory> restaurantCategories;

}
