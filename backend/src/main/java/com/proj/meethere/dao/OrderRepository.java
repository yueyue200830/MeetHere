package com.proj.meethere.dao;
import com.proj.meethere.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Order Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

    @Query(value = "select * from orderlist where order_approved = 0 ",nativeQuery = true)
    List<Order> selectUnapprovedOrder();

    @Modifying
    @Query(value = "update orderlist set order_approved = 1 where id = :id",nativeQuery = true)
    int updateOrderApproved(@Param("id") int id);

    @Query(value = "select * from orderlist where id = :id and order_approved = 0",nativeQuery = true)
    List<Order> selectSpecificUnapprovedOrder(@Param("id") int id);



}
