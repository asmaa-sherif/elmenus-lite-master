package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_type")
public class UserType extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;

    @Column(name = "user_type_name", nullable = false, unique = true)
    private String userTypeName;

    public UserType(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
