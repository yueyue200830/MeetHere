package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.request.UserModifyPassword;
import com.proj.meethere.request.UserRequest;
import com.proj.meethere.response.UserResponse;
import com.proj.meethere.unitTest.serviceTest.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Tresaresa, Yiqing Tao
 * @Date 2019/10/16 14:07
 */
@org.springframework.stereotype.Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/changeUserRole/{id}",  method = RequestMethod.GET)
    @ResponseBody
    public int changeUserRole(@PathVariable String id) {
            int iD = Integer.parseInt(id);
            return userService.updateUserRole(iD);

    }
    @RequestMapping(value = "/managerLoginCheck", method = RequestMethod.POST)
    @ResponseBody
    public int checkPwd(@RequestBody UserRequest userRequest){
        System.out.println("user check");
        return userService.checkManagerValid(userRequest.getUser_name(), userRequest.getUser_key());
    }

    @RequestMapping(value = "/modifyManagerPassword", method = RequestMethod.POST)
    @ResponseBody
    public int modifyPassword(@RequestBody UserModifyPassword userModifyPassword) {
        return userService.changeUserKey(userModifyPassword.getOldPassword(), userModifyPassword.getUserName(), userModifyPassword.getNewPassword());
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public List<UserResponse> getUser(){
        System.out.println("receive get user");
        return userService.getUserInfo();
    }

    @RequestMapping(value = "/deleteUser/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public int deleteUser(@PathVariable String temp) {
            int id = Integer.parseInt(temp);
            return userService.deleteSpecificUser(Integer.parseInt(temp));

    }

    @RequestMapping(value = "/searchUser/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserResponse> getSpecificUserInfo(@PathVariable String condition) {
        return userService.selectSpecificUserInfo(Integer.parseInt(condition));
    }

}
