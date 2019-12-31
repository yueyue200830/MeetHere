package com.proj.meethere.service;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.entity.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Yiqing Tao
 * @date 2019-12-20 0:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    OrderRepository orderRepository;

    Order newOrder;
    @Before
    public void init() {
        newOrder = new Order(10,"123456789012",12,1,1,"2019-10-29",0,120);
    }

    @Test
    public void unapproved_list_should_be_selected() {
        List<Order> orders = new ArrayList<>();
        orders.add(newOrder);
        when(orderRepository.selectSpecificUnapprovedOrder(10)).thenReturn(orders);
        List<Order> result = orderService.searchUnapprovedOrder(10);
        Assert.assertEquals(1, result.size());
        Mockito.verify(orderRepository,times(1)).selectSpecificUnapprovedOrder(10);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void update_approved_should_be_executed() {
        when(orderRepository.updateOrderApproved(10)).thenReturn(1);
        int result = orderService.approveOrder(10);
        Assert.assertEquals(1, result);
        verify(orderRepository,times(1)).updateOrderApproved(10);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void get_unapproved_order_should_be_executed() {
        List<Order> unApprovedOrders = new ArrayList<>();
        unApprovedOrders.add(newOrder);
        when(orderRepository.selectUnapprovedOrder()).thenReturn(unApprovedOrders);
        List<Order> orders = orderService.getUnapprovedOrder();
        Assert.assertEquals(1, orders.size());
        verify(orderRepository,times(1)).selectUnapprovedOrder();
        verifyNoMoreInteractions(orderRepository);
    }
    @Test
    public void order_should_not_be_updated_when_id_below_0() {
        int result = orderService.approveOrder(-1);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void order_should_not_be_searched_when_id_below_0() {
        List<Order> result = orderService.searchUnapprovedOrder(-1);
        assertAll(()->assertEquals(0, result.size()));
        verifyNoMoreInteractions(orderRepository);
    }

}
