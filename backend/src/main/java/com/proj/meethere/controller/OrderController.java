package com.proj.meethere.controller;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.entity.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Order> getUnapprovedOrder(){
        List<Order> unapprovedOrder = orderRepository.selectUnapprovedOrder();
//        List<String> result = new ArrayList<>();
//        for (Order order : unapprovedOrder) {
//            JSONObject jsonObject = new JSONObject(order);
//            result.add(jsonObject.toString());
//        }
        return unapprovedOrder;
    }

    @RequestMapping(value = "/approveOrder/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public int approveOrder(@PathVariable String temp) {
        return orderRepository.updateOrderApproved(Integer.parseInt(temp));
    }

    @RequestMapping(value = "/searchUnapproveOrder/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> searchUnapproveOrder(@PathVariable String condition) {
        List<Order> currentOrder = orderRepository.selectSpecificUnapprovedOrder(Integer.parseInt(condition));
//        List<String> result = new ArrayList<>();
//        for(Order order:currentOrder) {
//            JSONObject jsonObject = new JSONObject(order);
//            result.add(jsonObject.toString());
//        }
        return currentOrder;
    }


}
