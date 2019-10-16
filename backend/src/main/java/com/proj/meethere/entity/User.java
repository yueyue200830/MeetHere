package com.proj.meethere.entity;

import javax.persistence.Entity;
import org.springframework.data.annotation.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

/**
 * @Author Tresaresa
 * @Date 2019/10/16 13:15
 */
@Entity
public class User {
    @Id
   // @GeneratedValue
    private Integer id;
    private String user_name;
    private String user_key;
    private Integer type;
    private String user_photo;

    public User(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }
}
