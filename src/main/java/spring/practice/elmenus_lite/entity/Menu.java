package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy ="menu" , cascade = CascadeType.ALL)
    private List<MenuItem> items = new ArrayList<>();

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "active")
    private Boolean active;
}
