package spring.practice.elmenus_lite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.practice.elmenus_lite.controller.CartController;
import spring.practice.elmenus_lite.dto.cart.CartDto;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.CartService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(jsonPath("$.message").value("Cart deleted successfully"));
    }

    @Test
    void testDeleteCart_invalidId() throws Exception {
        mockMvc.perform(delete("/api/v1/cart/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testDeleteCart_notFound() throws Exception {
        Mockito.doThrow(new NotFoundCustomException("Cart not found")).when(cartService).deleteCart(2L);

        mockMvc.perform(delete("/api/v1/cart/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cart not found"));
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
                .thenThrow(new NotFoundCustomException("Cart not found"));

        mockMvc.perform(get("/api/v1/cart/customer/200"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cart not found"));
    }
}
