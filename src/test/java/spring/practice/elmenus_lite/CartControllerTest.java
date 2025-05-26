package spring.practice.elmenus_lite;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.practice.elmenus_lite.controller.CartController;
import spring.practice.elmenus_lite.dto.CartDto;
import spring.practice.elmenus_lite.service.CartService;
import spring.practice.elmenus_lite.controller.cart.CartController;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.cart.CartService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CART_CLEARED_SUCCESSFULLY;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CART_NOT_FOUND;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartDto sampleCart;

    @BeforeEach
    void setup() {
        sampleCart = new CartDto();
        sampleCart.setCartId(1L);
        sampleCart.setCustomerId(100L);
        sampleCart.setGrandTotal(50.0);
    }


    @Test
    void testDeleteCart_success() throws Exception {
        Mockito.when(cartService.deleteCart(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(CART_CLEARED_SUCCESSFULLY.getMessage()));
    }

    @Test
    void testDeleteCart_invalidId() throws Exception {
        mockMvc.perform(delete("/api/v1/cart/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testDeleteCart_notFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException(CART_NOT_FOUND.getMessage())).when(cartService).deleteCart(2L);

        mockMvc.perform(delete("/api/v1/cart/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(CART_NOT_FOUND.getMessage()));
    }

    @Test
    void testGetCartByCustomerId_success() throws Exception {
        Mockito.when(cartService.getCartByCustomerId(100L)).thenReturn(sampleCart);

        mockMvc.perform(get("/api/v1/cart/customer/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.cartId").value(1L))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testGetCartByCustomerId_invalidId() throws Exception {
        mockMvc.perform(get("/api/v1/cart/customer/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testGetCartByCustomerId_notFound() throws Exception {
        Mockito.when(cartService.getCartByCustomerId(200L))
                .thenThrow(new EntityNotFoundException(CART_NOT_FOUND.getMessage()));

        mockMvc.perform(get("/api/v1/cart/customer/200"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(CART_NOT_FOUND.getMessage()));
    }
}
