package spring.practice.elmenus_lite.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.entity.Customer;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.mapper.CartMapper;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return mapToDto(cart);
    }

    @Override
    public CartDto addCart(CartDto cartDto) {
        Cart cart = mapToEntity(cartDto);
        Cart savedCart = cartRepository.save(cart);
        return mapToDto(savedCart);
    }

    @Override
    public CartDto updateCart(Long id, CartDto cartDto) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getCustomer().getCustomerId().equals(cartDto.getCustomerId())) {
            Customer newCustomer = customerRepository.findById(cartDto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            cart.setCustomer(newCustomer);
        }


        Cart updatedCart = cartRepository.save(cart);
        return mapToDto(updatedCart);
    }

    @Override
    public Boolean deleteCart(Long id) {
        if (!this.cartRepository.existsById(id)) {
            throw new NotFoundCustomException("Cart not found");
        }
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CartDto getCartByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new NotFoundCustomException("Cart not found"));
        return mapToDto(cart);
    }


    private CartDto mapToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setCartId(cart.getCartId());
        dto.setCustomerId(cart.getCustomer().getCustomerId());
//        dto.setCustomerName(cart.getCustomer().getUser().getFullName());

        if (cart.getCartItems() != null) {
            List<CartItemDto> itemDtos = cart.getCartItems().stream().map(item -> {
                CartItemDto itemDto = new CartItemDto();
                itemDto.setCartItemId(item.getCartItemId());
                itemDto.setMenuItemId(item.getMenuItem().getMenuItemId());
                itemDto.setProductName(item.getMenuItem().getItemName());
                itemDto.setPrice(item.getMenuItem().getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setTotal(item.getMenuItem().getPrice() * item.getQuantity());
                return itemDto;
            }).collect(Collectors.toList());

            dto.setCartItems(itemDtos);
            double grandTotal = itemDtos.stream().mapToDouble(CartItemDto::getTotal).sum();
            dto.setGrandTotal(grandTotal);
        } else {
            dto.setCartItems(new ArrayList<>());
            dto.setGrandTotal(0.0);
        }

        return dto;
    }

    private Cart mapToEntity(CartDto dto) {
        Cart cart = new Cart();

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        cart.setCustomer(customer);


        return cart;
    }
}
