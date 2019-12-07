package com.proj.meethere.controller;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Order;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    @Autowired
    private RevenueRepository revenueRepository;

    // add
    // form：场馆id，日期， slot， 用户手机 ；用户id
    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    @ResponseBody
    public int addNewOrder(@RequestParam("addOrderForm") String addOrderForm, @RequestParam("id") int id) throws ParseException {
        System.out.println("addd");
        JSONObject jsonObject = new JSONObject(addOrderForm);
        String rvnName = jsonObject.getString("revenue");
        String phone = jsonObject.getString("phoneNumber");
        String date = jsonObject.getString("date");
        int slot = jsonObject.getInt("time");
        int room = jsonObject.getInt("room");
        int price = jsonObject.getInt("price");

        int rvnId = revenueRepository.searchIdByName(rvnName);
        //String newDate = "2019-12-09";
        // 使用 yyyy-MM-dd 格式的日期
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date d = sdf.parse(date);
        //String newDate = sdf.format(d);
        return orderRepository.insertNewOrder(id, phone, rvnId, room, slot, date, price);
    }

    // 显示的表格
    // 参数： 场馆id、日期
    // 二维数组： 有预约为钱 没预约为0 slot从1开始
    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailable(@RequestParam("revenueName") String revenueName, @RequestParam("date") String date) throws ParseException {
        // 使用 yyyy-MM-dd 格式的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date d = sdf.parse(date);
        String newDate = sdf.format(d);
        System.out.println(newDate);
        int revenueId = revenueRepository.searchIdByName(revenueName);
        List<Order> orders = orderRepository.selectByRevenueAndDate(revenueId, newDate);

        // 如果没有已预约的订单，返回空
        if (orders.isEmpty()) {
            return "";
        }

        Revenue revenue = revenueRepository.getSpecificRvn(revenueId).get(0);

        int price = revenue.getRvnPrice();

        // 行数为timeslot 列数为总房间数
        int rows = 12, cols = revenue.getRvnRoomnum();
        Integer available[][] = new Integer[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                available[i][j] = price;
            }
        }

        // 假设数据库中的timeslot和room都从1开始编号
        for(int i = 0; i < orders.size(); i++) {
            int timeslot = orders.get(i).getTimeSlot();
            int room = orders.get(i).getRvnRoomNum();
            available[timeslot - 1][room - 1] = 0;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("availble", available);

        return jsonObject.toString();

        //return "";

    }


    // 我的订单
    // 接受id 返回我的所有订单
    @RequestMapping(value = "/getMyOrder", method = RequestMethod.POST)
    @ResponseBody
    public String getMyOrder(@RequestParam("id") int id) {
        System.out.println("getall");
        List<Order> myOrders = orderRepository.selectOrderById(id);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(myOrders);

        return jsonArray.toString();
    }

    // 修改订单手机号
    // 接收订单id 和 新的手机号
    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET)
    @ResponseBody
    public int updatePhone(@RequestParam("phone") String phone, @RequestParam("id") int id) {
        System.out.println("update");
        return orderRepository.updatePhoneById(phone, id);
    }

    // 删除订单
    // 接收订单id
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    @ResponseBody
    public int deleteOrder(@RequestParam("deleteOrderId") int deleteOrderId) {
        System.out.println("delete");
        return orderRepository.deleteById(deleteOrderId);
    }
}
