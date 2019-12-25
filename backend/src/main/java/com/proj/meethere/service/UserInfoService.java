package com.proj.meethere.service;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:51
 */
@Service
public class UserInfoService {

    @Autowired
    UserRepository userRepository;

    public int checkUserNameExist(String userName) {
        List<User> users = userRepository.selectUserByName(userName);
        if (users.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int checkUserNameNew(String userName, int id) {
        List<User> users = userRepository.selectUserByNameAndId(userName, id);
        if (users.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int updateUserById(int id, String userKey, String userName) {
        return userRepository.updateUserById(userName, userKey, id);
    }

    public int insertNewUser(String userName, String userPass, int userType) {
        return userRepository.insertNewUser(userName, userPass, userType, null);
    }

    public int loginValidation(String userName, String userKey) {
        List<User> user = userRepository.selectUserByNameAndKey(userName, userKey);
        if (user.isEmpty()) {
            return -1;
        } else {
            return user.get(0).getId();
        }
    }

    public int updateUserPhoto(String userPhoto, int id) {
        return userRepository.updateUserPhotoById(userPhoto, id);
    }

    public Blob selectUserPhoto(int id) {
        return userRepository.selectSpecificUser(id).get(0).getUserPhoto();
    }
 }
