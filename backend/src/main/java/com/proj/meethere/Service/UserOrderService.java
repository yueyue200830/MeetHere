package com.proj.meethere.service;

import com.proj.meethere.dao.OrderRepository;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Order;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:50
 */
@Service
public class UserOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public int addNewOrder(int userId, String phone, int rvnId, int room, int slot, String date, int price) {
        return orderRepository.insertNewOrder(userId, phone, rvnId, room, slot, date, price);
    }

    public List<Order> selectOrderById(int id) {
        return orderRepository.selectOrderById(id);
    }

    public int updatePhone(String phone, int id) {
        return orderRepository.updatePhoneById(phone, id);
    }

    public int deleteOrder(int deleteOrderId) {
        return orderRepository.deleteById(deleteOrderId);
    }

    public List<Order> selectOrderByRevenueAndDate(int rvnId, String date) {
        return orderRepository.selectOrderByRevenueAndDate(rvnId, date);
    }
}
