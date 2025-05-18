package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "menu_item")
public class MenuItem extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "menu_item_name")
    private String itemName;

    @Column(name = "price")
    private Double price;

    @Column(name = "available")
    private boolean available;

    public MenuItem(Menu menu, String itemName, Double price, boolean available) {
        this.available = available;
        this.price = price;
        this.itemName = itemName;
        this.menu = menu;
    }

    public String getMenuName() {
        return menu.getMenuName();
    }


}

