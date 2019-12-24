package com.proj.meethere.response;

import lombok.Data;

/**
 * @author Yiqing Tao
 * @date 2019-12-24 13:40
 */
@Data
public class UserResponse {
    private int id;
    private String userName;
    private int userType;

    public UserResponse(int id, String userName, int userType) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
    }
}
