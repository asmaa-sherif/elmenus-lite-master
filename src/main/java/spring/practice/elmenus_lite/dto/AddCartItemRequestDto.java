package spring.practice.elmenus_lite.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequestDto {
    private Long customerId;
    private Long menuItemId;
    private Integer quantity;
}
