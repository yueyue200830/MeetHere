package com.proj.meethere.dao;
import com.proj.meethere.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * Message Repository
 */
@Repository
public interface MessageRepositroy extends JpaRepository<Message,Integer> {

    @Query(value = "select * from message where msg_visibility = 0",nativeQuery = true)
    List<Message> selectAllMessage();

    @Query(value = "select * from message where id = :id",nativeQuery = true)
    List<Message> selectSpecificMessage(@Param("id") int id);

    @Modifying
    @Query(value = "Update message set msg_visibility = 1 where id = :id",nativeQuery = true)
    int setMessageVisibility(@Param("id") int id);

    @Modifying
    @Query(value = "insert into message (msg_content, msg_time, msg_visilibility, user_id, msg_title) " +
            "value (:content, now(), false, :user_id, :title)", nativeQuery=true)
    int insertNewMessage(@Param("content") String content, @Param("user_id") int user_id, @Param("title") String title);

}