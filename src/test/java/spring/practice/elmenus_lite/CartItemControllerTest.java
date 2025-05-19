package spring.practice.elmenus_lite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.practice.elmenus_lite.controller.CartItemController;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.UpdateItemQuantityRequestBody;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.CartItemService;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CartItemController.class)
public class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItemDto itemDto;

    @BeforeEach
    void setUp() {
        itemDto = new CartItemDto();
        itemDto.setCartItemId(1L);
        itemDto.setMenuItemId(101L);
        itemDto.setProductName("Burger");
        itemDto.setPrice(15.0);
        itemDto.setQuantity(2);
        itemDto.setTotal(30.0);
    }

    @Test
    void testGetCartItemById_success() throws Exception {
        Mockito.when(cartItemService.getCartItemById(1L)).thenReturn(itemDto);

        mockMvc.perform(get("/api/v1/cartItem/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Burger"))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void testAddCartItem_success() throws Exception {
        Mockito.when(cartItemService.addCartItem(any(CartItemDto.class))).thenReturn(itemDto);

        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menuItemId").value(101L));
    }

    @Test
    void testUpdateCartItem_success() throws Exception {
        Mockito.when(cartItemService.updateCartItemById(eq(1L), any(CartItemDto.class))).thenReturn(itemDto);

        mockMvc.perform(put("/api/v1/cartItem/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Burger"));
    }

    @Test
    void testDeleteCartItem_success() throws Exception {
        Mockito.doNothing().when(cartItemService).deleteCartItemById(1L);

        mockMvc.perform(delete("/api/v1/cartItem/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateItemQuantity_success() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(5);

        itemDto.setQuantity(5);
        itemDto.setTotal(75.0);

        Mockito.when(cartItemService.updateCartItemQuantity(1L, 1L, 5)).thenReturn(itemDto);

        mockMvc.perform(patch("/api/v1/cartItem/1/1/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.quantity").value(5));
    }

    @Test
    void testUpdateItemQuantity_invalidQuantity() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(0);

        mockMvc.perform(patch("/api/v1/cartItem/1/1/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testUpdateItemQuantity_notFound() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(3);

        Mockito.when(cartItemService.updateCartItemQuantity(1L, 999L, 3))
                .thenThrow(new NotFoundCustomException("Cart Item not found"));

        mockMvc.perform(patch("/api/v1/cartItem/1/999/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("can not update quantity"));
    }
}
