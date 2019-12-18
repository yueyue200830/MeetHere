package com.proj.meethere.controller;

import com.proj.meethere.service.UserInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Tresaresa
 * @Date 2019/12/4 15:16
 */
@Controller
@CrossOrigin(origins = "*")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/getUserIdByName", method = RequestMethod.GET)
    @ResponseBody
    public int getUserIdByName(@RequestParam("user_name") String user_name) {
        return userInfoService.getUserIdByName(user_name);
    }

    @RequestMapping(value = "/updateUserById", method = RequestMethod.GET)
    @ResponseBody
    public int updateUserById(@RequestParam("id") int id, @RequestParam("updateForm") String updateForm) {
        JSONObject jsonObject = new JSONObject(updateForm);
        String userName = jsonObject.getString("name");
        String userKey = jsonObject.getString("pass");
        return userInfoService.updateUserById(id, userKey, userName);
    }

    /**
     * @param form 注册用户的userName，userKey
     * @return 失败返回-1
     */
    @RequestMapping(value = "/Register", method = RequestMethod.GET)
    @ResponseBody
    public int register(@RequestParam("form") String form) {
        JSONObject jsonObject = new JSONObject(form);
        String userName = jsonObject.getString("name");
        String userPass = jsonObject.getString("password");
        // 默认注册用户类型为0（普通用户），头像为default.jpg
        return userInfoService.insertNewUser(userName, userPass, 0, "default.jpg");
    }

    /**
     * @param login 用户登陆时输入的信息，包含userName，userKey
     * @return 成功返回id，失败返回-1
     */
    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    @ResponseBody
    public int login(@RequestParam("login") String login) {
        JSONObject jsonObject = new JSONObject(login);
        String userName = jsonObject.getString("name");
        String userKey = jsonObject.getString("password");
        return userInfoService.loginValidation(userName, userKey);
    }
}
