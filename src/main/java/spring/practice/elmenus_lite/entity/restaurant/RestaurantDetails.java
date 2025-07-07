package spring.practice.elmenus_lite.entity.restaurant;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;
import spring.practice.elmenus_lite.entity.AbstractEntity;

import java.time.Duration;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "restaurant_details")
public class RestaurantDetails extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_details_id")
    private Long detailId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;


    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "estimated_delivery_time")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration estimatedDeliveryTime;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    private Point location;
}
