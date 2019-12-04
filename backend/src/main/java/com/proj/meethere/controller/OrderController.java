package com.proj.meethere.controller;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.entity.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * Order Controller
 */
@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/getUnapproveOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getUnapprovedOrder(){
        List<Order> unapprovedOrder = orderRepository.selectUnapprovedOrder();
        List<String> result = new ArrayList<>();
        for (Order order : unapprovedOrder) {
            JSONObject jsonObject = new JSONObject(order);
            result.add(jsonObject.toString());
        }
        return result;
    }

    @RequestMapping(value = "/approveOrder", method = RequestMethod.POST)
    @ResponseBody
    public int approveOrder(@RequestParam(name = "id") int id) {
        return orderRepository.updateOrderApproved(id);
    }

    @RequestMapping(value = "/seaechUnapproveOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<String> searchUnapproveOrder(@RequestParam(name = "id") int id) {
        List<Order> currentOrder = orderRepository.selectSpecificUnapprovedOrder(id);
        List<String> result = new ArrayList<>();
        for(Order order:currentOrder) {
            JSONObject jsonObject = new JSONObject(order);
            result.add(jsonObject.toString());
        }
        return result;
    }
    
}
