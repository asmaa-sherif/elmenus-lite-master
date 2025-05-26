package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.menu.MenuRequestDto;
import spring.practice.elmenus_lite.dto.menu.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto createMenu(MenuRequestDto menuRequest);

    MenuResponseDto getMenuById(Long id);

    List<MenuResponseDto> getAllMenus();

    MenuResponseDto updateMenu(Long id, MenuRequestDto menuRequest);

    void deleteMenu(Long id);

    List<MenuResponseDto> getMenusByRestaurantId(Long restaurantId);
}
