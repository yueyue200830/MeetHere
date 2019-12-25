package com.proj.meethere.controller;

import com.proj.meethere.service.OrderService;
import com.proj.meethere.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29s
 * Order Controller
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/getUnapproveOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> getUnapprovedOrder(){
        return orderService.getUnapprovedOrder();
    }

    @RequestMapping(value = "/approveOrder/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public int approveOrder(@PathVariable String temp) {
        try{
            int id = Integer.parseInt(temp);
            return orderService.approveOrder(id);
        } catch (NumberFormatException e) {
            throw e;
        }

    }

    @RequestMapping(value = "/searchUnapproveOrder/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> searchUnapproveOrder(@PathVariable String condition) {
        try{
            int id = Integer.parseInt(condition);
            return orderService.searchUnapprovedOrder(id);
        } catch (NumberFormatException e) {
            throw e;
        }

    }


}
