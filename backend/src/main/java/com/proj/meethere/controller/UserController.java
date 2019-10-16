package com.proj.meethere.controller;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/10/16 14:07
 */
@RestController
public class UserController {

    UserRepository userRepository;

    @RequestMapping("/")
    private String userList(){
        //return userRepository.find();
        return "hello";
    }
}
