package spring.practice.elmenus_lite.dto.menu;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuDto {
    private Long menuId;
    //private Restaurant restaurant;
    private String menuName;
}
