package com.proj.meethere.controller;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Tresaresa, Yiqing Tao
 * @Date 2019/10/16 14:07
 */
@org.springframework.stereotype.Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    public Boolean checkPwd(@RequestBody String info){
        JSONObject jsonObject = new JSONObject(info);
        String userName = jsonObject.getString("user_name");
        String pwd = jsonObject.getString("user_key");
        String truePwd = userRepository.searchUserKeyByName(userName);
        System.out.println("true pwd " + truePwd);
        return truePwd.equals(pwd);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUser(){
        System.out.println("receive getuser ");
        List<User> allUser = userRepository.selectAllUserInfo();
//        List<String> userInfo = new ArrayList<>();
//        for(int i = 0;i < allUser.size();i++){
//            User currentUser = allUser.get(i);
//            JSONObject jo = new JSONObject(currentUser);
//            userInfo.add(jo.toString());
//        }
        //System.out.println("user info :" + userInfo);
        return allUser;
    }

    @RequestMapping(value = "/deleteUser/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public int deleteUser(@PathVariable String temp) {
        //System.out.println(temp);
        return userRepository.deleteSpecificUser(Integer.parseInt(temp));
    }

    @RequestMapping(value = "/searchUser/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public String getSpecificUserInfo(@PathVariable String condition) {
        List<User> userList = userRepository.selectSpecificUser(Integer.parseInt(condition));
        String result = "";
        if (userList.size() == 1) {
            User currentUser = userList.get(0);
            JSONObject jsonObject = new JSONObject(currentUser);
            result = jsonObject.toString();
        } else {
            System.out.println("duplicate user");
            result = "duplicate";
        }
        return result;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public int addNewUser(@RequestParam("user_name") String user_name, @RequestParam("user_key") String user_key,
                          @RequestParam("user_type") int user_type, @RequestParam("user_photo") String user_photo) {
        return userRepository.insertNewUser(user_name, user_key, user_type, user_photo);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserById(@RequestParam("user_name") String user_name, @RequestParam("user_key") String user_key,
                              @RequestParam("user_photo") String user_photo, @RequestParam("user_id") int user_id) {
        return userRepository.updateUserById(user_name, user_key, user_photo, user_id);
    }
}
