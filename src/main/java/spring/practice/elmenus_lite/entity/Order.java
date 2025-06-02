package spring.practice.elmenus_lite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    @JsonIgnore
    private OrderStatus orderStatus;

    /*
    @ManyToOne
    @JoinColumn(name = "order_tracking_id")
    private OrderTracking tracking;

     */

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    private Double discountAmount;
    private Double subtotal;
    private Double total;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
}

