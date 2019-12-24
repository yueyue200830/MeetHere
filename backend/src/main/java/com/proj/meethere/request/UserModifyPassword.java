package com.proj.meethere.request;

import lombok.Data;

/**
 * @author Yiqing Tao
 * @date 2019-12-24 8:56
 */
@Data
public class UserModifyPassword {
    private String userName;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
