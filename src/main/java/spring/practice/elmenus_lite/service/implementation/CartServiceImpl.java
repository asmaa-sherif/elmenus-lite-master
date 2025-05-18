package spring.practice.elmenus_lite.service.implementation;

import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.entity.Cart;
import spring.practice.elmenus_lite.entity.CartItem;
import spring.practice.elmenus_lite.entity.Customer;
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

    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return mapToDto(cart);
    }

    @Override
    public CartResponseDto addCart(CartResponseDto cartDto) {
        Cart cart = mapToEntity(cartDto);
        Cart savedCart = cartRepository.save(cart);
        return mapToDto(savedCart);
    }

    @Override
    public CartResponseDto updateCart(Long id, CartResponseDto cartDto) {
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
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CartResponseDto mapToDto(Cart cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getCartId());
        dto.setCustomerId(cart.getCustomer().getCustomerId());
//        dto.setCustomerName(cart.getCustomer().getUser().getFullName());

        if (cart.getCartItems() != null) {
            List<CartItemResponseDto> itemDtos = cart.getCartItems().stream().map(item -> {
                CartItemResponseDto itemDto = new CartItemResponseDto();
                itemDto.setProductName(item.getMenuItem().getItemName());
                itemDto.setPrice(item.getMenuItem().getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setTotal(item.getMenuItem().getPrice() * item.getQuantity());
                return itemDto;
            }).collect(Collectors.toList());

            dto.setItems(itemDtos);
            double grandTotal = itemDtos.stream().mapToDouble(CartItemResponseDto::getTotal).sum();
            dto.setGrandTotal(grandTotal);
        } else {
            dto.setItems(new ArrayList<>());
            dto.setGrandTotal(0.0);
        }

        return dto;
    }

    private Cart mapToEntity(CartResponseDto dto) {
        Cart cart = new Cart();

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        cart.setCustomer(customer);


        return cart;
    }
}
