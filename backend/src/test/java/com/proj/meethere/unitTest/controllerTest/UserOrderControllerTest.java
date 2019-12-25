package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.controller.UserOrderController;
import com.proj.meethere.service.UserOrderService;
import com.proj.meethere.service.UserRevenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Tresaresa
 * @Date 2019/12/20 18:40
 */
@WebMvcTest(UserOrderController.class)
class UserOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserOrderService userOrderService;

    @MockBean
    private UserRevenueService userRevenueService;

    @BeforeEach
    void init() {

    }

    @ParameterizedTest
    @MethodSource("phoneIdProvider")
    void should_update_phone_of_specific_order(String phone, int id) throws Exception {
        mockMvc.perform(get("/updatePhone")
                .param("phone", phone).param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userOrderService, times(1)).updatePhone(phone, id);
    }

    @ParameterizedTest
    @MethodSource("orderIdProvider")
    void should_delete_specific_order(int id) throws Exception {
        mockMvc.perform(get("/deleteOrder")
                .param("deleteOrderId", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userOrderService, times(1)).deleteOrder(id);
    }

    static Stream<Arguments> orderIdProvider() {
        return Stream.of(Arguments.of(5),
                         Arguments.of(0));
    }

    static Stream<Arguments> phoneIdProvider() {
        return Stream.of(Arguments.of("12344", 5));
    }
}
