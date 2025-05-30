package spring.practice.elmenus_lite.service.cart;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.dto.cart.CartItemDto;
import spring.practice.elmenus_lite.dto.menu.MenuItemDto;
import spring.practice.elmenus_lite.dto.CartDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.mapper.CartMapper;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.service.CartService;

import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CART_NOT_FOUND;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public Boolean deleteCart(Long id) {
        if (!this.cartRepository.existsById(id)) {
            throw new EntityNotFoundException(CART_NOT_FOUND.getMessage() + id);
        }
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public CartDto getCartByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND.getMessage() + customerId));
        return this.cartMapper.cartToCartDto(cart);
    }
}
