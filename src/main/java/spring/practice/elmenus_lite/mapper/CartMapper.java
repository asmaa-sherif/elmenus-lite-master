package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.entity.Cart;


import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
     Cart cartDtoToCart(CartDto dto);

    CartDto cartToCartDto(Cart cart);

    List<Cart> cartDtoListToCartList(List<CartDto> dto);

    List<CartDto> cartListToCartDtoList(List<Cart> carts);
}
