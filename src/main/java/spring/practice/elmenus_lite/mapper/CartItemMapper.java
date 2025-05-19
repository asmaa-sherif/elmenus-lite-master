package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.entity.CartItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartMapper.class)
public interface CartItemMapper {
     CartItem cartItemDtoToCartItem(CartItemDto dto);

    CartItemDto cartItemToCartItemDto(CartItem cart);

    List<CartItem> cartItemDtoListToCartItemList(List<CartItemDto> dto);

    List<CartItemDto> cartItemListToCartItemDtoList(List<CartItem> carts);
}
