package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatus extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderStatusId;
    private String orderStatusName;

    public OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}



