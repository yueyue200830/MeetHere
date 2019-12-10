package com.proj.meethere.Service;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 22:05
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getUnapprovedOrder() {
        return orderRepository.selectUnapprovedOrder();
    }

    public int approveOrder(int id) {
        return orderRepository.updateOrderApproved(id);
    }

    public List<Order> searchUnapprovedOrder(int id) {
        return orderRepository.selectSpecificUnapprovedOrder(id);
    }
}
