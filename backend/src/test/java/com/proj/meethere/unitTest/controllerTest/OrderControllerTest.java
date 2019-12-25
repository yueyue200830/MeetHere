package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.controller.OrderController;
import com.proj.meethere.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Yiqing Tao
 * @date 2019-12-20 8:20
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @Test
    public void should_get_unapproved_orders() throws Exception {
        ResultActions perform = mockMvc.perform(post("/getUnapproveOrder"));
        perform.andExpect(status().isOk());
        verify(orderService,times(1)).getUnapprovedOrder();
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void should_approve_order() throws Exception {
        ResultActions perform = mockMvc.perform(get("/approveOrder/{temp}","1"));
        perform.andExpect(status().isOk());
        verify(orderService,times(1)).approveOrder(1);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void should_search_unapproved_order_by_id() throws Exception{
        ResultActions perform = mockMvc.perform(get("/searchUnapproveOrder/{condition}","1"));
        perform.andExpect(status().isOk());
        verify(orderService,times(1)).searchUnapprovedOrder(1);
        verifyNoMoreInteractions(orderService);
    }
}
