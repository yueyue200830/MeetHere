package com.proj.meethere.controller;

import com.proj.meethere.service.UserOrderService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @Author Tresaresa
 * @Date 2019-12-02
 * User Order Controller
 */

@Controller
@CrossOrigin(origins = "*")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    /**
     * @param addOrderForm 订单信息，包括 用户信息(phone), 订单信息(date, price, slot), 场馆信息(rvnName, room)
     * @param userId 用户id
     * @return 返回受影响的行数
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    @ResponseBody
    public int addNewOrder(@RequestParam("addOrderForm") String addOrderForm, @RequestParam("id") int userId) {
        JSONObject jsonObject = new JSONObject(addOrderForm);
        String rvnName = jsonObject.getString("revenue");
        String phone = String.valueOf(jsonObject.getLong("phoneNumber"));
        String date = jsonObject.getString("date");
        int slot = jsonObject.getInt("timeSlot");
        int room = jsonObject.getInt("room");
        int price = jsonObject.getInt("price");

        return userOrderService.addNewOrder(userId, phone, rvnName, room, slot, date, price);
    }

    /**
     * @param rvnName 场馆名称
     * @param date 日期
     * @return 返回二维数组，每行代表一个slot，每列代表一个场馆；若无预约，元素值为该订单价格，若有预约，为0
     * @throws ParseException
     */
    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailable(@RequestParam("revenueName") String rvnName, @RequestParam("date") String date) {
        Integer[][] available = userOrderService.getAvailable(rvnName, date);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("available", available);
        return jsonObject.toString();
    }

    /**
     * @param id 用户id
     * @return 返回当前用户的所有订单
     */
    @RequestMapping(value = "/getMyOrder", method = RequestMethod.GET)
    @ResponseBody
    public String getMyOrder(@RequestParam("id") int id) {
        JSONArray jsonArray = userOrderService.selectOrderById(id);
        if(jsonArray == null) {
            return (new JSONArray()).toString();
        } else {
            return jsonArray.toString();
        }
    }

    /**
     * @param phone 新手机号
     * @param id 订单id
     * @return 返回受影响行数
     */
    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET)
    @ResponseBody
    public int updatePhone(@RequestParam("phone") String phone, @RequestParam("id") int id) {
        return userOrderService.updatePhone(phone, id);
    }

    /**
     * @param deleteOrderId 需要删除的订单id
     * @return 返回受影响行数
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    @ResponseBody
    public int deleteOrder(@RequestParam("deleteOrderId") int deleteOrderId) {
        return userOrderService.deleteOrder(deleteOrderId);
    }
}
