package com.proj.meethere.controller;

import com.proj.meethere.request.UserModifyPassword;
import com.proj.meethere.request.UserRequest;
import com.proj.meethere.service.UserService;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.json.JSONObject;
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
        return userService.updateUserRole(id);
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
    public List<User> getUser(){
        System.out.println("receive getuser ");
        return userService.getUserInfo();
    }

    @RequestMapping(value = "/deleteUser/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public int deleteUser(@PathVariable String temp) {
        //System.out.println(temp);
        return userService.deleteSpecificUser(Integer.parseInt(temp));
    }

    @RequestMapping(value = "/searchUser/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getSpecificUserInfo(@PathVariable String condition) {
        return userService.selectSpecificUserInfo(Integer.parseInt(condition));
    }

//    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
//    @ResponseBody
//    public int addNewUser(@RequestParam("user_name") String user_name, @RequestParam("user_key") String user_key,
//                          @RequestParam("user_type") int user_type, @RequestParam("user_photo") String user_photo) {
//        return userService.addUser(user_name, user_key, user_type, user_photo);
//    }

//    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
//    @ResponseBody
//    public int updateUserById(@RequestParam("user_name") String user_name, @RequestParam("user_key") String user_key,
//                              @RequestParam("user_photo") String user_photo, @RequestParam("user_id") int user_id) {
//        return userRepository.updateUserById(user_name, user_key, user_photo, user_id);
//    }
}
