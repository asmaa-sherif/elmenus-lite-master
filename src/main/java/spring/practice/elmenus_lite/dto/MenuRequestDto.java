package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;


    @Getter
    @Setter
    public class MenuRequestDto {
        private Long restaurantId;
        private String menuName;
    }

