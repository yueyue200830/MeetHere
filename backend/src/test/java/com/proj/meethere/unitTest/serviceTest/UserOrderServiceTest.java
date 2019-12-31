package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Order;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @Author Tresaresa
 * @Date 2019/12/25 22:31
 */
@SpringBootTest
class UserOrderServiceTest {

    @Autowired
    private UserOrderService userOrderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private RevenueRepository revenueRepository;

    static final int orderId = 1;
    static final int userId = 10;
    static final String phone = "12345678912";
    static final int room = 3;
    static final int rvnId = 1;
    static final int slot = 1;
    static final String date = "2019-12-30";
    static final int price = 50;
    static final String name = "篮球馆";
    static final int roomnum = 4;
    static final String intro = "篮球馆介绍";


    static private Order order;
    static private List<Order> orders = new ArrayList<>();
    static private Revenue revenue;
    static private List<Revenue> revenues = new ArrayList<>();

    @BeforeAll
    static void init() {
        order = new Order(orderId, userId, phone, room,rvnId,slot,date,0,price);
        orders.add(order);
        revenue = new Revenue(rvnId, name, roomnum, intro, price);
        revenues.add(revenue);
    }

    @Test
    void should_return_1_when_add_one_order_success() {
        // stubbing
        when(revenueRepository.searchIdByName(name)).thenReturn(rvnId);
        when(orderRepository.insertNewOrder(userId, phone, rvnId, room, slot, date, price)).thenReturn(1);

        // calling
        int result = userOrderService.addNewOrder(userId, phone, name, room, slot, date, price);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(revenueRepository, times(1)).searchIdByName(name);
        verify(orderRepository, times(1)).insertNewOrder(userId, phone, rvnId, room, slot, date, price);

        verifyNoMoreInteractions(orderRepository);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    void should_return_0_when_add_one_order_failed() {
        // stubbing
        when(revenueRepository.searchIdByName(name)).thenReturn(rvnId);
        when(orderRepository.insertNewOrder(userId, phone, rvnId, room, slot, date, price)).thenReturn(0);

        // calling
        int result = userOrderService.addNewOrder(userId, phone, name, room, slot, date, price);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(revenueRepository, times(1)).searchIdByName(name);
        verify(orderRepository, times(1)).insertNewOrder(userId, phone, rvnId, room, slot, date, price);

        verifyNoMoreInteractions(orderRepository);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    void should_return_json_when_select_specific_order() {
        // stubbing
        when(revenueRepository.getAllRvnInfo()).thenReturn(revenues);
        when(orderRepository.selectOrderById(orderId)).thenReturn(orders);

        // calling
        JSONArray result = userOrderService.selectOrderById(orderId);
        JSONObject firstObject = (result.getJSONObject(0));

        // verifying result
        assertTrue(firstObject.keySet().contains("orderDate"));
        assertTrue(firstObject.keySet().contains("timeSlot"));
        assertTrue(firstObject.keySet().contains("revenue"));
        assertTrue(firstObject.keySet().contains("rvnRoomNum"));
        assertTrue(firstObject.keySet().contains("orderPrice"));
        assertTrue(firstObject.keySet().contains("orderPhone"));
        assertTrue(firstObject.keySet().contains("orderApproved"));
        assertTrue(firstObject.keySet().contains("orderId"));

        // verifying invoked times
        verify(revenueRepository, times(1)).getAllRvnInfo();
        verify(orderRepository, times(1)).selectOrderById(anyInt());

        verifyNoMoreInteractions(orderRepository);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    void should_return_1_when_update_phone_success() {
        String newPhone = "39849933023";

        // stubbing
        when(orderRepository.updatePhoneById(newPhone, orderId)).thenReturn(1);

        // calling
        int result = userOrderService.updatePhone(newPhone, orderId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(orderRepository, times(1)).updatePhoneById(newPhone, orderId);

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void should_return_0_when_update_phone_failed() {
        String newPhone = "39849933023";

        // stubbing
        when(orderRepository.updatePhoneById(newPhone, orderId)).thenReturn(0);

        // calling
        int result = userOrderService.updatePhone(newPhone, orderId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(orderRepository, times(1)).updatePhoneById(newPhone, orderId);

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void should_return_1_when_delete_order_success() {
        // stubbing
        when(orderRepository.deleteById(orderId)).thenReturn(1);

        // calling
        int result = userOrderService.deleteOrder(orderId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(orderRepository, times(1)).deleteById(orderId);

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void should_return_0_when_delete_order_failed() {
        // stubbing
        when(orderRepository.deleteById(orderId)).thenReturn(0);

        // calling
        int result = userOrderService.deleteOrder(orderId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(orderRepository, times(1)).deleteById(orderId);

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void should_return_available_table_when_get_available() {
        // stubbing
        when(revenueRepository.searchIdByName(name)).thenReturn(rvnId);
        when(revenueRepository.getRevenueById(rvnId)).thenReturn(revenues);
        when(orderRepository.selectOrderByRevenueAndDate(rvnId, date)).thenReturn(orders);

        // calling
        Integer[][] result = userOrderService.getAvailable(name, date);

        // verifying result
        assertEquals(12, result.length);
        assertEquals(roomnum, result[0].length);
        Integer[][] right = new Integer[][]{{price, price, 0, price}, {price, price, price, price}, {price, price, price, price},
                                            {price, price, price, price}, {price, price, price, price}, {price, price, price, price},
                                            {price, price, price, price}, {price, price, price, price}, {price, price, price, price},
                                            {price, price, price, price}, {price, price, price, price}, {price, price, price, price}};
        assertArrayEquals(right, result);

        // verifying invoked times
        verify(revenueRepository, times(1)).searchIdByName(name);
        verify(revenueRepository, times(1)).getRevenueById(rvnId);
        verify(orderRepository, times(1)).selectOrderByRevenueAndDate(rvnId, date);

        verifyNoMoreInteractions(revenueRepository);
        verifyNoMoreInteractions(orderRepository);
    }
}
