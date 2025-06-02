package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.entity.Cart;


import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class, CustomerMapper.class})
public interface CartMapper {

    @Mapping(source = "customerId", target = "customer.customerId")
    @Mapping(source = "customerName", target = "customer.user.firstName")
    @Mapping(target = "cartItems", source = "cartItems")
    Cart cartDtoToCart(CartDto dto);

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "customer.user.firstName", target = "customerName")
    @Mapping(target = "cartItems", source = "cartItems")
    CartDto cartToCartDto(Cart cart);

    List<Cart> cartDtoListToCartList(List<CartDto> dto);

    List<CartDto> cartListToCartDtoList(List<Cart> carts);
}
