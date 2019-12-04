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

    // 显示的表格
    // 参数： 场馆id、日期
    // 二维数组： 有预约为钱 没预约为0 slot从1开始

    // add
    // form：场馆id，日期， slot， 用户手机 ；用户id

    // 我的订单
    // 接受id 返回我的所有订单

    // 修改订单手机号
    // 接收订单id 和 新的手机号

    // 删除订单
    // 接收订单id
}
