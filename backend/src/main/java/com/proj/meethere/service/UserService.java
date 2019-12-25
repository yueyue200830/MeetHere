package com.proj.meethere.service;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import com.proj.meethere.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 14:27
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserResponse> getUserInfo() {
        List<User> uList = userRepository.selectAllUserInfo();
        List<UserResponse> urList = new ArrayList<>();
        for(User user: uList) {
            urList.add(new UserResponse(user.getId(), user.getUserName(), user.getUserType()));
        }
        return urList;
    }

    public int deleteSpecificUser(int userId) {
        return userRepository.deleteSpecificUser(userId);
    }

    public List<UserResponse> selectSpecificUserInfo(int userId) {
        List<UserResponse> uList = new ArrayList<>();
        if(userId < 0) {
            return uList;
        } else {
            List<User> userList = userRepository.selectSpecificUser(userId);
            for (User user: userList) {
                UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getUserType());
                uList.add(userResponse);
            }
            return uList;
        }
    }

    //public int addUser(String name, String key, int type, String photo) {
    //    return userRepository.insertNewUser(name, key, type, photo);
    //}

    public int updateUserRole(int userId) {
        System.out.println(userId);
        return userRepository.updateUserRole(1, userId);
    }

    public int checkManagerValid(String userName, String userKey) {
        List<User> uList = userRepository.selectUserAll(userName);
        if(uList.size() == 0) {
            return 0;
        } else {
            if (userRepository.selectUserType(userName) == 1) {
                System.out.println("user name exists");
                String key = userRepository.searchUserKeyByName(userName);
                if (key.equals(userKey)) {
                    System.out.println("key good");
                    return 1;
                } else {
                    System.out.println("key wrong");
                    return 0;
                }
            } else {
                System.out.println("type wrong");
                return 0;
            }
        }
    }

    public int changeUserKey(String oldUserKey, String userName, String newUserKey) {
        String orginKey = userRepository.searchUserKeyByName(userName);
        if(! "".equals(orginKey)) {
            if(oldUserKey.equals(orginKey)) {
               return userRepository.updateUserKey(newUserKey, userName);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
}
