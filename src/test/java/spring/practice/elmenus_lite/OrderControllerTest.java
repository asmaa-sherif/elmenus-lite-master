package spring.practice.elmenus_lite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.practice.elmenus_lite.controller.cart.CartController;
import spring.practice.elmenus_lite.controller.order.OrderController;
import spring.practice.elmenus_lite.dto.order.OrderSummaryDto;
import spring.practice.elmenus_lite.service.order.OrderService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetOrderSummary() throws Exception {
        Long orderId = 1L;

        OrderSummaryDto summaryDto = new OrderSummaryDto();
        summaryDto.setOrderId(orderId);
        summaryDto.setStatus("PENDING");

        when(orderService.getOrderSummary(orderId)).thenReturn(summaryDto);

        mockMvc.perform(get("/api/v1/order/{orderId}/summary", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService).getOrderSummary(orderId);
    }



}
