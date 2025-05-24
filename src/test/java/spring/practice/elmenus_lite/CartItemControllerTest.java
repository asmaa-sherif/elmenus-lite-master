package spring.practice.elmenus_lite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.practice.elmenus_lite.controller.CartItemController;
import spring.practice.elmenus_lite.dto.CartItemRequestDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.handlerException.SaveOperationException;
import spring.practice.elmenus_lite.service.CartItemService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static spring.practice.elmenus_lite.enums.SuccessAndErrorMessage.CAN_NOT_UPDATE_QUANTITY;


@WebMvcTest(CartItemController.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartItemService cartItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItemRequestDto requestDto;


    @BeforeEach
    void setUp() {
        requestDto = new CartItemRequestDto();
        requestDto.setCustomerId(5L);
        requestDto.setMenuItemId(101L);
        requestDto.setQuantity(2);
    }



    @Test
    void testAddCartItem_success() throws Exception {
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(5L)
                .menuItemId(101L)
                .quantity(2)
                .build();

        Mockito.doNothing().when(cartItemService).addCartItem(cartItemRequest);

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
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(5L)
                .menuItemId(101L)
                .quantity(2)
                .build();

        Mockito.doThrow(new NotFoundCustomException("Customer not found"))
                .when(cartItemService).addCartItem(cartItemRequest);

        mockMvc.perform(post("/api/v1/cartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    void testAddCartItem_serverError() throws Exception {
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(5L)
                .menuItemId(101L)
                .quantity(2)
                .build();

        Mockito.doThrow(new RuntimeException("Unexpected error"))
                .when(cartItemService).addCartItem(cartItemRequest);

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
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(1L)
                .menuItemId(10L)
                .quantity(5)
                .build();

        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setMenuItemId(10L);

        CartItemDto updatedItem = new CartItemDto();
        updatedItem.setCartItemId(1L);
        updatedItem.setMenuItem(menuItemDto);
        updatedItem.setQuantity(5);
        updatedItem.setTotal(50.0);

        Mockito.when(cartItemService.updateCartItemQuantity(ArgumentMatchers.any()))
                .thenReturn(updatedItem);

        mockMvc.perform(patch("/api/v1/cartItem/updateItemQuantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Quantity updated successfully"))
                .andExpect(jsonPath("$.response.quantity").value(5));
    }

    @Test
    void testUpdateItemQuantity_invalidQuantity() throws Exception {
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(1L)
                .menuItemId(10L)
                .quantity(0) // invalid quantity
                .build();

        mockMvc.perform(patch("/api/v1/cartItem/updateItemQuantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid quantity"));
    }

    @Test
    void testUpdateItemQuantity_invalidCartItemId() throws Exception {
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(0L) // invalid cart item ID
                .menuItemId(10L)
                .quantity(5)
                .build();

        mockMvc.perform(patch("/api/v1/cartItem/updateItemQuantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid cart item ID"));
    }

    @Test
    void testUpdateItemQuantity_updateFailed() throws Exception {
        CartItemRequestDto cartItemRequest = CartItemRequestDto.builder()
                .cartItemId(1L)
                .menuItemId(1L)
                .quantity(3)
                .build();

        Mockito.when(cartItemService.updateCartItemQuantity(ArgumentMatchers.any()))
                .thenThrow(new SaveOperationException(CAN_NOT_UPDATE_QUANTITY.getMessage())); // failed update

        mockMvc.perform(patch("/api/v1/cartItem/updateItemQuantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("can not update quantity"));
    }



}
