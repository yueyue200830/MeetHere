package com.proj.meethere.controller;

import com.proj.meethere.service.UserInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;

/**
 * @Author Tresaresa
 * @Date 2019/12/4 15:16
 */
@Controller
@CrossOrigin(origins = "*")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @param userName 用户名
     * @return 判断该用户名是否已经存在于数据库中，存在则返回1，不存在则返回0
     */
    @RequestMapping(value = "/checkUserNameExist", method = RequestMethod.GET)
    @ResponseBody
    public int getUserIdByName(@RequestParam("user_name") String userName) {
        return userInfoService.checkUserNameExist(userName);
    }

    /**
     * @param userName 用户名
     * @param id 用户id
     * @return 判断该用户名是否已经存在于数据库中（排除编号为id的用户），存在则返回1，不存在则返回0
     */
    @RequestMapping(value = "/checkUserNameWithId", method = RequestMethod.GET)
    @ResponseBody
    public int getUserIdByName(@RequestParam("user_name") String userName, @RequestParam("id") int id) {
        return userInfoService.checkUserNameNew(userName, id);
    }

    /**
     * @param id 用户id
     * @param newName 新用户名
     */
    @RequestMapping(value = "/updateUserNameById", method = RequestMethod.GET)
    @ResponseBody
    public int updateUserNameById(@RequestParam("id") int id, @RequestParam("newName") String newName) {
        return userInfoService.updateUserNameById(id, newName);
    }

    /**
     *
     * @param id 用户id
     * @param updateForm 包括旧密码origionalPass、新密码pass
     * @return 失败返回-1，成功返回1
     */
    @RequestMapping(value = "/updateUserPassById", method = RequestMethod.GET)
    @ResponseBody
    public int updateUserPassById(@RequestParam("id") int id, @RequestParam("updateForm") String updateForm) {
        JSONObject jsonObject = new JSONObject(updateForm);
        String userKey = jsonObject.getString("pass");
        String origionalKey = jsonObject.getString("originalPass");
        return userInfoService.updateUserPassById(id, userKey, origionalKey);
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
        // 默认注册用户类型为0（普通用户），头像为空
        return userInfoService.insertNewUser(userName, userPass, 0);
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

    /**
     * @param file 头像文件
     * @param id 用户id
     * @return 返回受影响行数
     */
    @RequestMapping(value = "/UploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public int uploadNewPhoto(@RequestParam("file") MultipartFile file, @RequestParam("id") int id) throws IOException {
        // convert to base64 string
        String base64 = Base64.encodeBase64String(file.getBytes());
        base64 = "data:image/png;base64," + base64;
        return userInfoService.updateUserPhoto(base64, id);
    }

    /**
     * @param id 用户id
     * @return 用户头像(base64编码)
     */
    @RequestMapping(value = "/GetPhoto", method = RequestMethod.GET)
    @ResponseBody
    public String selectPhoto(@RequestParam("id") int id) throws UnsupportedEncodingException, SQLException {
        Blob blob = userInfoService.selectUserPhoto(id);
        if(blob == null) {
            return "";
        } else {
            return new String(blob.getBytes(1, (int)blob.length()), "UTF-8");
        }
    }
}
