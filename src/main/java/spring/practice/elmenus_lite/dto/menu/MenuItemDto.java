package spring.practice.elmenus_lite.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
