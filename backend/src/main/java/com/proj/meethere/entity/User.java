package com.proj.meethere.entity;

import javax.persistence.*;
import java.sql.Blob;

/**
 * @Author Tresaresa Yiqing Tao
 * @Date 2019/10/16 13:15
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_key")
    private String userKey;

    /* 0：普通用户 1：管理员 */
    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "user_photo")
    private Blob userPhoto;

    public User(){
    }

    public User(int userId, String userName, String userKey, Integer userType, Blob userPhoto) {
        this.id = userId;
        this.userName = userName;
        this.userKey = userKey;
        this.userType = userType;
        this.userPhoto = userPhoto;
    }

    public User(String userName, String userKey, Integer userType, Blob userPhoto) {
        this.userName = userName;
        this.userKey = userKey;
        this.userType = userType;
        this.userPhoto = userPhoto;
    }

    public User(String userName, String userKey, Integer userType) {
        this.userName = userName;
        this.userKey = userKey;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Blob getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Blob userPhoto) {
        this.userPhoto = userPhoto;
    }
}
