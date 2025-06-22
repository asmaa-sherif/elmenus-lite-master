package spring.practice.elmenus_lite.controller.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.menu.MenuRequestDto;
import spring.practice.elmenus_lite.dto.menu.MenuResponseDto;
import spring.practice.elmenus_lite.service.menu.MenuService;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuResponseDto>createMenu(@RequestBody MenuRequestDto dto){
        MenuResponseDto response = menuService.createMenu(dto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}/menus")
    public ResponseEntity<List<MenuResponseDto>> getMenusByRestaurant(@PathVariable Long restaurantId) {
        List<MenuResponseDto> menus = menuService.getMenusByRestaurantId(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {
        List<MenuResponseDto> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        MenuResponseDto menu = menuService.getMenuById(id);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDto menuRequest) {
        MenuResponseDto updatedMenu = menuService.updateMenu(id, menuRequest);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MenuResponseDto> changeMenuStatus(@PathVariable Long id, @RequestParam boolean active) {
        MenuResponseDto updatedMenu = menuService.setMenuActiveStatus(id, active);
        return ResponseEntity.ok(updatedMenu);
    }



}
