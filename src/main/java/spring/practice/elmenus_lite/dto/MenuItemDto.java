package spring.practice.elmenus_lite.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuItemDto {
    private Long menuItemId;
    private MenuDto menu;
    private String itemName;
    private Double price;
    private boolean available;
}
