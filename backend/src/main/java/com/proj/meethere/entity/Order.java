package com.proj.meethere.entity;
import javax.persistence.*;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * OrderList Entity
 */
@Entity
@Table(name="orderlist")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "user_id")
    int userId;

    @Column(name = "order_phone")
    String orderPhone;

    @Column(name = "rvn_room")
    int rvnRoomNum;

    @Column(name = "rvn_id")
    int rvnId;

    @Column(name = "order_timeslot")
    int timeSlot;

    @Column(name = "order_date")
    String orderDate;

    @Column(name = "order_approved")
    int orderApproved;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public int getRvnId() {
        return rvnId;
    }

    public void setRvnId(int rvnId) {
        this.rvnId = rvnId;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getRvnRoomNum() {
        return rvnRoomNum;
    }

    public void setRvnRoomNum(int rvnRoomNum) {
        this.rvnRoomNum = rvnRoomNum;
    }

    public int getOrderApproved() {
        return orderApproved;
    }

    public void setOrderApproved(int orderApproved) {
        this.orderApproved = orderApproved;
    }
}