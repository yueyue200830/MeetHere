package com.proj.meethere.controller;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/12/4 15:16
 */
@Controller
@CrossOrigin(origins = "*")
public class UserInfoController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/getUserIdByName", method = RequestMethod.GET)
    @ResponseBody
    public int getUserIdByName(@RequestParam("user_name") String user_name) {
        List<User> users = userRepository.selectUserByName(user_name);
        if (users.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    @RequestMapping(value = "/updateUserById", method = RequestMethod.GET)
    @ResponseBody
    public int updateUserById(@RequestParam("id") int id, @RequestParam("updateForm") String updateForm) {
        JSONObject jsonObject = new JSONObject(updateForm);
        String userName = jsonObject.getString("name");
        String userKey = jsonObject.getString("pass");
        return userRepository.updateUserById(userName, userKey, id);
    }

    // 注册（上传图片待定）

    // 登陆
    // 返回id，错误-1
    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    @ResponseBody
    public int login(@RequestParam("login") String login) {
        System.out.println("login");
        JSONObject jsonObject = new JSONObject(login);
        String userName = jsonObject.getString("name");
        String userKey = jsonObject.getString("password");
        List<User> user = userRepository.selectUserByNameAndId(userName, userKey);
        if (user.isEmpty()) {
            return -1;
        } else {
            return user.get(0).getId();
        }
    }
}
