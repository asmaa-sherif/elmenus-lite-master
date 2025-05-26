package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    private String code;
    private String promotionName;
    private Double discountPercent;
    private Double maxDiscount;
    private Boolean active;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}



