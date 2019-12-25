package com.proj.meethere.controllerTest;

import com.proj.meethere.controller.UserOrderController;
import com.proj.meethere.service.UserOrderService;
import org.junit.Test;
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

    @ParameterizedTest
    @MethodSource("addFormIdProvider")
    void should_add_one_order(String form, int userId, String phone, String rvnName, int room, int slot, String date, int price) throws Exception {
        mockMvc.perform(get("/addOrder")
                .param("addOrderForm", form).param("id", String.valueOf(userId)))
                .andExpect(status().isOk());
        verify(userOrderService, times(1)).addNewOrder(userId, phone, rvnName, room, slot, date, price);
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

    @Test
    void should_return_available_table(String rvnName, String date) throws Exception {
        mockMvc.perform(get("/getAvailable")
                .param("revenueName", rvnName).param("date", date))
                .andExpect(status().isOk());
        verify(userOrderService, times(1)).getAvailable(rvnName, date);
    }

    @Test
    void should_return_specific_user_order() throws Exception {
        int id = 5;
        mockMvc.perform(get("/getMyOrder")
                .param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userOrderService, times(1)).selectOrderById(id);
    }

    static Stream<Arguments> addFormIdProvider() {
        return Stream.of(
                Arguments.of("{\"revenue\":\"篮球馆\",\"phoneNumber\":\"12312312312\"," +
                        "\"date\":\"2019-12-10\",\"timeSlot\":\"2\"," +
                        "\"room\":\"4\",\"price\":\"30\"}", 5, "12312312312", "篮球馆", 4, 2, "2019-12-10", 30));
    }

    static Stream<Arguments> orderIdProvider() {
        return Stream.of(Arguments.of(5),
                         Arguments.of(0));
    }

    static Stream<Arguments> phoneIdProvider() {
        return Stream.of(Arguments.of("12344", 5));
    }
}
