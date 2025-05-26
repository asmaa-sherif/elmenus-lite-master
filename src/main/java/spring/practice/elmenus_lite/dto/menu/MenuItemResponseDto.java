package spring.practice.elmenus_lite.dto.menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemResponseDto {
    private Long menuItemId;
    private Double price;
    private boolean available;
    private Long menuId;
}
