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
 * @Author Tresaresa
 * @Date 2019-12-02
 * User Order Controller
 */

@Controller
@CrossOrigin(origins = "*")
public class UserOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public int addNewOrder(@RequestParam("user_id") int user_id, @RequestParam("order_phone") String order_phone, @RequestParam("rvn_id") int rvn_id,
                           @RequestParam("rvn_roomnum") int rvn_roomnum, @RequestParam("order_timeslot") int order_timeslot,
                           @RequestParam("order_date") String order_date){
        return orderRepository.insertNewOrder(user_id, order_phone, rvn_id, rvn_roomnum, order_timeslot, order_date);
    }
}
