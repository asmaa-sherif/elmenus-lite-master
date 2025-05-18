package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String password;

//    @Transient
//    public String getFullName() {
//        return firstName + " " + lastName;
//    }

    @Column(name = "last_login")
    private Instant lastLogin;

//    @Column(name = "is_deleted")
//    private Boolean isDeleted;
}
