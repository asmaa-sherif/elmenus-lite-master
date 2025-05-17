package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
