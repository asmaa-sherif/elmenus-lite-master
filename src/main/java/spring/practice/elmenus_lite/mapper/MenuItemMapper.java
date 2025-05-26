package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.entity.MenuItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItem menuItemDtoToMenuItem(MenuItemDto dto);

    MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);

    List<MenuItem> menuItemDtoListToMenuItemList(List<MenuItemDto> dto);

    List<MenuItemDto> menuItemListToMenuItemDtoList(List<MenuItem> menuItems);
}
