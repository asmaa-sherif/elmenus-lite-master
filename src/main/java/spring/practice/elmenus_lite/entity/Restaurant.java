package spring.practice.elmenus_lite.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

}
