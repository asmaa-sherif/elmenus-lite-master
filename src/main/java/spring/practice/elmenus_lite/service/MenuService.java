package spring.practice.elmenus_lite.service;

import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.entity.Menu;
import spring.practice.elmenus_lite.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getMenusByRestaurantId(Long restaurantId) {
        return menuRepository.findByRestaurantRestaurantId(restaurantId);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }
}
