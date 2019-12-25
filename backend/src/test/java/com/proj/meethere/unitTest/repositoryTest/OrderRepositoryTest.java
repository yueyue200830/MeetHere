package com.proj.meethere.unitTest.repositoryTest;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.entity.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-19 10:38
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class OrderRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    OrderRepository orderRepository;
    private Order newOrder;

    @Before
    public void init() {
        newOrder = new Order(10,"123456789012",12,1,1,"2019-10-29",0,120);
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
    }

    @Test
    public void should_get_unapproved_order() {
        this.testEntityManager.persist(newOrder);
        List<Order> orders = orderRepository.selectSpecificUnapprovedOrder(newOrder.getId());
        Assert.assertEquals(1,orders.size());
        Order curOrder = orders.get(0);
        Assert.assertSame(newOrder, curOrder);
//        Assert.assertEquals(10,curOrder.getUserId());
//        Assert.assertEquals("123456789012",curOrder.getOrderPhone());
//        Assert.assertEquals(12,curOrder.getRvnRoomNum());
//        Assert.assertEquals(1,curOrder.getRvnId());
//        Assert.assertEquals(1,curOrder.getTimeSlot());
//        Assert.assertEquals("2019-10-29",curOrder.getOrderDate());
//        Assert.assertEquals(0,curOrder.getOrderApproved());
//        Assert.assertEquals(120,curOrder.getOrderPrice());
    }
    @Test
    public void order_should_be_approved() {
        this.testEntityManager.persist(newOrder);
        int result = orderRepository.updateOrderApproved(newOrder.getId());
        Assert.assertEquals(1,result);
        List<Order> orders = orderRepository.selectSpecificUnapprovedOrder(newOrder.getId());
        Assert.assertEquals(0, orders.size());
    }

    @Test
    public void order_should_be_deleted() {
        this.testEntityManager.persist(newOrder);
        int result = orderRepository.deleteById(newOrder.getId());
        Assert.assertEquals(1, result);
        List<Order> orders = orderRepository.selectOrderById(newOrder.getId());
        Assert.assertEquals(0, orders.size());
    }

    @Test
    public void order_should_be_selected_the_same() {
        this.testEntityManager.persist(newOrder);
        List<Order> orders = orderRepository.selectOrderById(newOrder.getUserId());
        Assert.assertEquals(1, orders.size());
        Order curOrder = orders.get(0);
        Assert.assertEquals(10,curOrder.getUserId());
        Assert.assertEquals("123456789012",curOrder.getOrderPhone());
        Assert.assertEquals(12,curOrder.getRvnRoomNum());
        Assert.assertEquals(1,curOrder.getRvnId());
        Assert.assertEquals(1,curOrder.getTimeSlot());
        Assert.assertEquals("2019-10-29",curOrder.getOrderDate());
        Assert.assertEquals(0,curOrder.getOrderApproved());
        Assert.assertEquals(120,curOrder.getOrderPrice());
    }



}
