package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String label;
    private String street;
    private String city;
    private String floor;
    private String apartment;
    private String additionalDirection;
    private String state;
    private String country;
    private String zipCode;
    private String location;

    private Boolean isDefault;
}


