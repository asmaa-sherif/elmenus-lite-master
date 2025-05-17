package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "price")
    private Double price;

    @Column(name = "available")
    private boolean available;

    public String getMenuName() {
        return menu.getMenuName();
    }
}

