package com.proj.meethere.dao;

import com.proj.meethere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Tresaresa Yiqing Tao
 * @Date 2019/10/16 13:15
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select user_key from user where user_name = :userName",nativeQuery = true)
    String searchUserKeyByName(@Param("userName") String userName);

    @Query(value = "select * from user",nativeQuery = true)
    List<User> selectAllUserInfo();

    @Modifying
    @Query(value = "delete from user where id = :id",nativeQuery = true)
    int deleteSpecificUser(@Param("id") int id);

    @Query(value = "select * from user where id = :id",nativeQuery = true)
    List<User> selectSpecificUser(@Param("id") int id);

//    /**
//     * description 根据用户名查询
//     *
//     * @param user_name 用户名
//     * @ return
//     */
//    @Query("select u from User u where u.name=:name")
//    public User findByName(@Param("user_name") String user_name);
//
//    /**
//     * description 查询全部用户
//     *
//     * @ return List<User>
//     */
//    @Query("select t from User t")
//    public List<User> find();
//
//    /**
//     * description 根据用户ID删除用户
//     *
//     * @param id 用户ID
//     * @ return int
//     */
//    @Modifying
//    @Transactional
//    @Query("delete from User u where u.id=:id")
//    public int deleteUserById(@Param("id") Integer id);
//
//    /**
//     * description 加入新用户
//     *
//     * @param user_name 用户名
//     * @param user_key 用户密码
//     * @param user_type 用户类别（1 为管理员，2 为普通用户）
//     * @param user_photo 用户头像路径
//     * @ return int
//     */
//    @Modifying
//    @Transactional
//    @Query(value="insert into user (user_name, user_key, user_type, user_photo) " +
//            "value (?,?,?,?)", nativeQuery=true)
//    public int createUser(@Param("user_name") String user_name, @Param("user_key") String user_key,
//                          @Param("user_type") Integer user_type, @Param("user_photo") String user_photo );
}
