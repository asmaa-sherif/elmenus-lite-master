package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.MenuItemResponseDto;
import java.util.List;

public interface MenuItemService {
    MenuItemResponseDto getMenuItemById(Long id);
    List<MenuItemResponseDto> getAllMenuItems();
    MenuItemResponseDto addMenuItem(MenuItemResponseDto menuItemDto);
    MenuItemResponseDto updateMenuItem(Long id, MenuItemResponseDto menuItemDto);
    void deleteMenuItem(Long id);
}
