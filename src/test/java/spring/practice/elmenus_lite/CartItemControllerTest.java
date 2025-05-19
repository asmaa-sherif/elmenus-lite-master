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
import spring.practice.elmenus_lite.dto.AddCartItemRequestDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.dto.UpdateItemQuantityRequestBody;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.CartItemService;
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
    private AddCartItemRequestDto requestDto;


    @BeforeEach
    void setUp() {

        requestDto = new AddCartItemRequestDto();
        requestDto.setCustomerId(5L);
        requestDto.setMenuItemId(101L);
        requestDto.setQuantity(2);
    }



    @Test
    void testAddCartItem_success() throws Exception {
        Mockito.doNothing().when(cartItemService).addCartItem(5L, 101L, 2);

        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cart item added successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testAddCartItem_invalidInput() throws Exception {
        requestDto.setCustomerId(0L); // invalid input
        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testAddCartItem_customerNotFound() throws Exception {
        Mockito.doThrow(new NotFoundCustomException("Customer not found"))
                .when(cartItemService).addCartItem(5L, 101L, 2);

        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void testAddCartItem_serverError() throws Exception {
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(cartItemService).addCartItem(5L, 101L, 2);

        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false));
    }


    @Test
    void testDeleteCartItem_success() throws Exception {
        mockMvc.perform(delete("/api/v1/cartItem/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cart item deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testDeleteCartItem_invalidId() throws Exception {
        mockMvc.perform(delete("/api/v1/cartItem/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid Cart Item ID"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testDeleteCartItem_notFound() throws Exception {
        Mockito.doThrow(new NotFoundCustomException("Cart item not found"))
                .when(cartItemService).deleteCartItemById(999L);

        mockMvc.perform(delete("/api/v1/cartItem/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Cart item not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testDeleteCartItem_serverError() throws Exception {
        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(cartItemService).deleteCartItemById(2L);

        mockMvc.perform(delete("/api/v1/cartItem/2"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }


    @Test
    void testUpdateItemQuantity_success() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(5);

        CartItemDto updatedItem = new CartItemDto();
        updatedItem.setCartItemId(10L);
        updatedItem.setMenuItem(new MenuItemDto());
        updatedItem.setQuantity(5);
        updatedItem.setTotal(50.0);

        Mockito.when(cartItemService.updateCartItemQuantity(1L, 10L, 5))
                .thenReturn(updatedItem);

        mockMvc.perform(patch("/api/v1/cartItem/1/10/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Quantity updated successfully"))
                .andExpect(jsonPath("$.response.quantity").value(5));
    }

    @Test
    void testUpdateItemQuantity_invalidQuantity() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(0); // invalid

        mockMvc.perform(patch("/api/v1/cartItem/1/10/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid quantity"));
    }

    @Test
    void testUpdateItemQuantity_invalidCartItemId() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(3);

        mockMvc.perform(patch("/api/v1/cartItem/1/0/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid cart item ID"));
    }

    @Test
    void testUpdateItemQuantity_invalidCartId() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(3);

        mockMvc.perform(patch("/api/v1/cartItem/0/5/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid cart ID"));
    }

    @Test
    void testUpdateItemQuantity_updateFailed() throws Exception {
        UpdateItemQuantityRequestBody body = new UpdateItemQuantityRequestBody();
        body.setQuantity(3);

        Mockito.when(cartItemService.updateCartItemQuantity(1L, 5L, 3)).thenReturn(null); // failed update

        mockMvc.perform(patch("/api/v1/cartItem/1/5/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("can not update quantity"));
    }



}
