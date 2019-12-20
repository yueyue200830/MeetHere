package com.proj.meethere.service;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 14:27
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUserInfo() {
        return userRepository.selectAllUserInfo();
    }

    public int deleteSpecificUser(int userId) {
        return userRepository.deleteSpecificUser(userId);
    }

    public List<User> selectSpecificUserInfo(int userId) {
        return userRepository.selectSpecificUser(userId);
    }

    //public int addUser(String name, String key, int type, String photo) {
    //    return userRepository.insertNewUser(name, key, type, photo);
    //}

    public String checkUser(String userName) {
        return userRepository.searchUserKeyByName(userName);
    }
    
}
