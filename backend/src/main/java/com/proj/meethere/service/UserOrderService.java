package com.proj.meethere.service;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Order;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:50
 */
@Service
public class UserOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    public int addNewOrder(int userId, String phone, String rvnName, int room, int slot, String date, int price) {
        int rvnId = revenueRepository.searchIdByName(rvnName);
        return orderRepository.insertNewOrder(userId, phone, rvnId, room, slot, date, price);
    }

    public JSONArray selectOrderById(int id) {
        List<Order> orders = orderRepository.selectOrderById(id);
        List<Revenue> revenues = revenueRepository.getAllRvnInfo();
        
        Map<Integer, String> rvnMap = new HashMap<>();
        for (Revenue revenue : revenues) {
            rvnMap.put(revenue.getId(), revenue.getRvnName());
        }

        JSONArray jsonArray = new JSONArray();
        for (Order order : orders) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderDate", order.getOrderDate());
            jsonObject.put("timeSlot", order.getTimeSlot());
            jsonObject.put("revenue", rvnMap.get(order.getRvnId()));
            jsonObject.put("rvnRoomNum", order.getRvnRoomNum());
            jsonObject.put("orderPrice", order.getOrderPrice());
            jsonObject.put("orderPhone", order.getOrderPhone());
            jsonObject.put("orderApproved", order.getOrderApproved());
            jsonObject.put("orderId", order.getId());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public int updatePhone(String phone, int id) {
        return orderRepository.updatePhoneById(phone, id);
    }

    public int deleteOrder(int deleteOrderId) {
        return orderRepository.deleteById(deleteOrderId);
    }

    public Integer[][] getAvailable(String rvnName, String date) {
        int rvnId = revenueRepository.searchIdByName(rvnName);
        List<Order> orders = orderRepository.selectOrderByRevenueAndDate(rvnId, date);
        Revenue revenue = revenueRepository.getRevenueById(rvnId).get(0);
        int price = revenue.getRvnPrice();

        // 行数为timeslot 列数为总房间数
        int rows = 12, cols = revenue.getRvnRoomnum();
        Integer[][] available = new Integer[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                available[i][j] = price;
            }
        }

        // 假设数据库中的timeslot和room都从1开始编号
        for (int i = 0; i < orders.size(); i++) {
            int timeslot = orders.get(i).getTimeSlot();
            int room = orders.get(i).getRvnRoomNum();
            available[timeslot - 1][room - 1] = 0;
        }

        return available;
    }
}
