package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.menu.MenuRequestDto;
import spring.practice.elmenus_lite.dto.menu.MenuResponseDto;
import spring.practice.elmenus_lite.entity.Menu;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mapping(target = "restaurant", ignore = true)
    Menu toEntity(MenuRequestDto dto);

    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    MenuResponseDto toDto (Menu menu);
}
