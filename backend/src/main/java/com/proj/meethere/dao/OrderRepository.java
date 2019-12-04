package com.proj.meethere.dao;
import com.proj.meethere.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * Order Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

    @Query(value = "select * from orderlist where order_approved = 0 ",nativeQuery = true)
    List<Order> selectUnapprovedOrder();

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update orderlist set order_approved = 1 where id = :id",nativeQuery = true)
    int updateOrderApproved(@Param("id") int id);

    @Query(value = "select * from orderlist where id = :id and order_approved = 0",nativeQuery = true)
    List<Order> selectSpecificUnapprovedOrder(@Param("id") int id);

    @Modifying
    @Query(value = "insert into orderlist (user_id, order_phone, rvn_id, rvn_roomnum, order_timeslot, order_date, order_proved)" +
            "value (:user_id, :order_phone, :rvn_id, :rvn_roomnum, :order_timeslot, :order_date, false)", nativeQuery = true)
    int insertNewOrder(@Param("user_id") int user_id, @Param("order_phone") String order_phone, @Param("rvn_id") int rvn_id,
                       @Param("rvn_roomnum") int rvn_roomnum, @Param("order_timeslot") int order_timeslot,
                       @Param("order_date") String order_date);

}
