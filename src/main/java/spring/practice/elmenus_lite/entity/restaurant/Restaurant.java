package spring.practice.elmenus_lite.entity.restaurant;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.entity.AbstractEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "restaurant_name", nullable = false, unique = true, length = 100)
    private String restaurantName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RestaurantCategory> restaurantCategories = new HashSet<>();

}
