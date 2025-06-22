package spring.practice.elmenus_lite.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.menu.MenuRequestDto;
import spring.practice.elmenus_lite.dto.menu.MenuResponseDto;
import spring.practice.elmenus_lite.entity.Menu;
import spring.practice.elmenus_lite.entity.Restaurant;
import spring.practice.elmenus_lite.mapper.MenuMapper;
import spring.practice.elmenus_lite.repository.MenuRepository;
import spring.practice.elmenus_lite.repository.RestaurantRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
        private final RestaurantRepository restaurantRepository;
        private final MenuMapper menuMapper;

        @Autowired
        public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository, MenuMapper menuMapper) {
            this.menuRepository = menuRepository;
            this.restaurantRepository = restaurantRepository;
            this.menuMapper = menuMapper;
        }

        @Override
        public MenuResponseDto createMenu(MenuRequestDto menuRequest)
        {
            Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            Menu menu = menuMapper.toEntity(menuRequest);
            menu.setRestaurant(restaurant);

            Menu saved = menuRepository.save(menu);
            return menuMapper.toDto(saved);

        }

        @Override
        public MenuResponseDto getMenuById(Long id){
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
            return menuMapper.toDto(menu);
        }

        @Override
        public List<MenuResponseDto> getAllMenus(){
            List<Menu> menus = menuRepository.findAll();
            return menus.stream()
                    .map(menuMapper::toDto)
                    .toList();    }

        @Override
        public MenuResponseDto updateMenu(Long id, MenuRequestDto menuRequest){
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

            menu.setMenuName(menuRequest.getMenuName());

            if (menuRequest.getRestaurantId() != null) {
                Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId())
                        .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + menuRequest.getRestaurantId()));
                menu.setRestaurant(restaurant);
            }

            Menu updatedMenu = menuRepository.save(menu);
            return menuMapper.toDto(updatedMenu);
        }

        @Override
        public void deleteMenu(Long id){
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found with id: " + id);
        }
        menuRepository.deleteById(id);
        }

        @Override
        public  List<MenuResponseDto> getMenusByRestaurantId(Long restaurantId){
            List<Menu> menus = menuRepository.findByRestaurantRestaurantId(restaurantId);
            return menus.stream()
                    .map(menuMapper::toDto)
                    .collect(Collectors.toList());
        }

        @Override
        public MenuResponseDto setMenuActiveStatus(Long id, boolean isActive) {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));

            menu.setActive(isActive);
            Menu updatedMenu = menuRepository.save(menu);

            return menuMapper.toDto(updatedMenu);
        }
}


