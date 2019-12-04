package com.proj.meethere.dao;
import com.proj.meethere.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Transactional
    @Query(value = "insert into message (msg_content, msg_time, msg_visibility, user_id, msg_title) " +
            "value (:content, now(), false, :user_id, :title)", nativeQuery=true)
    int insertNewMessage(@Param("content") String content, @Param("user_id") int user_id, @Param("title") String title);

    /**
     * Find latest number of comments.
     * @param number The number of comments.
     * @return List of latest comments.
     */
    @Query(value = "SELECT * FROM message where msg_visibility=1 ORDER BY msg_time DESC LIMIT :number ", nativeQuery = true)
    List<Message> findLatestMessages(@Param("number") int number);

    /**
     * Get more comments based on the date time.
     * @param lastTime The last comment's time.
     * @param number The number of comments.
     * @return List of next comments.
     */
    @Query(value = "SELECT * FROM message WHERE msg_time < :lastTime and msg_visibility=1 ORDER BY msg_time DESC LIMIT :number ", nativeQuery = true)
    List<Message> findMoreMessagesBefore(@Param("lastTime") String lastTime, @Param("number") int number);
}
