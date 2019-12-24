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

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "Update message set msg_visibility = 1 where id = :id",nativeQuery = true)
    int setMessageVisibility(@Param("id") int id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
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
     * Find specific user's latest number of comments.
     * @param number The number of comments.
     * @param user_id The id of user.
     * @return List of latest comments.
     */
    @Query(value = "SELECT * FROM message where user_id=:user_id ORDER BY msg_time DESC LIMIT :number ", nativeQuery = true)
    List<Message> findLatestMessagesById(@Param("number") int number, @Param("user_id") int user_id);

    /**
     * Get more comments based on the date time.
     * @param lastTime The last comment's time.
     * @param number The number of comments.
     * @return List of next comments.
     */
    @Query(value = "SELECT * FROM message WHERE msg_time < :lastTime and msg_visibility=1 ORDER BY msg_time DESC LIMIT :number ", nativeQuery = true)
    List<Message> findMoreMessagesBefore(@Param("lastTime") String lastTime, @Param("number") int number);

    /**
     * Get specific user's more comments based on the date time.
     * @param lastTime The last comment's time.
     * @param number The number of comments.
     * @param user_id The id of user.
     * @return List of next comments.
     */
    @Query(value = "SELECT * FROM message WHERE msg_time < :lastTime and user_id=:user_id ORDER BY msg_time DESC LIMIT :number ", nativeQuery = true)
    List<Message> findMoreMessagesBeforeById(@Param("lastTime") String lastTime, @Param("number") int number, @Param("user_id") int user_id);

    /**
     * Delete Message by Id.
     * @param id The id of message.
     * @return Number of rows effected.
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "delete from message where id=:id", nativeQuery = true)
    int deleteMessage(@Param("id") int id);

    /**
     * Update Message content and title by id.
     * @param msg_content New content.
     * @param msg_title New title.
     * @param id The id of message.
     * @return Number of rows effected.
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update message set msg_content=:msg_content, msg_title=:msg_title where id=:id", nativeQuery = true)
    int updateMessageById(@Param("msg_content") String msg_content, @Param("msg_title") String msg_title, @Param("id") int id);
}
