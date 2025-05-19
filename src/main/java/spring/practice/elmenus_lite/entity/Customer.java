package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;
import spring.practice.elmenus_lite.enums.Gender;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone")
    private String phone;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
