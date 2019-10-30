package com.proj.meethere.dao;

import com.proj.meethere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Tresaresa, Yiqing Tao
 * @Date 2019/10/16 13:15
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select user_key from user where user_name = :userName", nativeQuery = true)
    String searchUserKeyByName(@Param("userName") String userName);

    @Query(value = "select * from user", nativeQuery = true)
    List<User> selectAllUserInfo();

    @Modifying
    @Query(value = "delete from user where id = :id", nativeQuery = true)
    int deleteSpecificUser(@Param("id") int id);

    @Query(value = "select * from user where id = :id", nativeQuery = true)
    List<User> selectSpecificUser(@Param("id") int id);

    @Modifying
    @Query(value = "insert into user (user_name, user_key, user_type, user_photo)" +
            "value (:user_name, :user_key, :user_type, :user_photo)", nativeQuery = true)
    int insertNewUser(@Param("user_name") String user_name, @Param("user_key") String user_key,
                      @Param("user_type") int user_type, @Param("user_photo") String user_photo);

    @Modifying
    @Query(value = "update user set user_name=:user_name, user_key=:user_key, user_photo=:user_photo" +
            "where user_id=:user_id", nativeQuery = true)
    int updateUserById(@Param("user_name") String user_name, @Param("user_key") String user_key,
                       @Param("user_photo") String user_photo, @Param("user_id") int user_id);
}