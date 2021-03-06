package com.proj.meethere.dao;

import com.proj.meethere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
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

    @Transactional
    @Modifying
    @Query(value = "delete from user where id = :id", nativeQuery = true)
    int deleteSpecificUser(@Param("id") int id);

    @Query(value = "select * from user where id = :id", nativeQuery = true)
    List<User> selectSpecificUser(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update user set user_key=:user_key where user_name=:user_name", nativeQuery = true)
    int updateUserKey(@Param("user_key") String userKey, @Param("user_name") String userName );

    @Modifying
    @Transactional
    @Query(value = "update user set user_type=:user_type where id=:id", nativeQuery = true)
    int updateUserRole(@Param("user_type") int userType, @Param("id") int userId );

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "insert into user (user_name, user_key, user_type, user_photo)" +
            " value (:user_name, :user_key, :user_type, :user_photo)", nativeQuery = true)
    int insertNewUser(@Param("user_name") String user_name, @Param("user_key") String user_key,
                      @Param("user_type") int user_type, @Param("user_photo") Blob user_photo);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update user set user_name=:user_name where id=:user_id", nativeQuery = true)
    int updateUserNameById(@Param("user_id") int user_id, @Param("user_name") String user_name);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update user set user_key=:user_key where id=:user_id", nativeQuery = true)
    int updateUserPassById(@Param("user_id") int user_id, @Param("user_key") String user_key);

    @Query(value = "select * from user where user_name=:user_name", nativeQuery = true)
    List<User> selectUserByName(@Param("user_name") String user_name);

    @Query(value = "select * from user where user_name=:user_name and id<>:id", nativeQuery = true)
    List<User> selectUserByNameAndId(@Param("user_name") String user_name, @Param("id") int id);

    @Query(value = "select * from user where user_name=:name and user_key=:key", nativeQuery = true)
    List<User> selectUserByNameAndKey(@Param("name") String name, @Param("key") String key);

    @Query(value = "select * from user where id=:id and user_key=:key", nativeQuery = true)
    List<User> selectUserByIdAndKey(@Param("id") int id, @Param("key") String key);

    @Transactional
    @Modifying
    @Query(value = "update user set user_photo=:user_photo where id=:user_id", nativeQuery = true)
    int updateUserPhotoById(@Param("user_photo") String user_photo, @Param("user_id") int user_id);

    @Query(value = "select user_type from user where user_name =:user_name", nativeQuery = true)
    int selectUserType(@Param("user_name") String userName);

    @Query(value = "select user_type from user where id=:user_id", nativeQuery = true)
    int selectUserTypeById(@Param("user_id") int userId);

    @Query(value = "select * from user where user_name = :user_name", nativeQuery = true)
    List<User> selectUserAll(@Param("user_name") String userName);
}