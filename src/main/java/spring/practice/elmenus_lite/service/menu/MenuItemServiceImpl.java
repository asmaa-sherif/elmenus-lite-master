package spring.practice.elmenus_lite.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.entity.MenuItem;
import spring.practice.elmenus_lite.repository.MenuItemRepository;

import java.util.List;


@Service
public abstract class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItem> searchMenuItemsByName(String itemName) {
        return menuItemRepository.findByItemNameContainingIgnoreCase(itemName);
    }
}


