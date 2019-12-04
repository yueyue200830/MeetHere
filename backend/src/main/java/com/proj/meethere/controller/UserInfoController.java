package com.proj.meethere.controller;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/getUserIdByName", method = RequestMethod.POST)
    @ResponseBody
    public int getUserIdByName(@RequestParam("user_name") String user_name) {
        List<User> users = userRepository.selectUserByName(user_name);
        if (users.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    @RequestMapping(value = "/updateUserById", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserById(@RequestParam("id") int id, @RequestParam("updateForm") String updateForm) {
       JSONObject jsonObject = new JSONObject(updateForm);
        String userName = jsonObject.getString("userName");
        String userKey = jsonObject.getString("userKey");
        return userRepository.updateUserById(userName, userKey, id);
    }

    // 注册（上传图片待定）

    // 登陆
    // 返回id，错误-1
}
