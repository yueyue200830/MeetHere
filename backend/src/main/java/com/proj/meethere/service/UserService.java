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

    /**
     * get all the users' information
     * @return a list of desired format of user information to front end.
     */
    public List<UserResponse> getUserInfo() {
        List<User> uList = userRepository.selectAllUserInfo();
        List<UserResponse> urList = new ArrayList<>();
        for(User user: uList) {
            urList.add(new UserResponse(user.getId(), user.getUserName(), user.getUserType()));
        }
        return urList;
    }

    /**
     * delete the user by id.
     * @param userId the user id
     * @return if the deletion operation is successful.
     */
    public int deleteSpecificUser(int userId) {
        if(userId < 0) {
            return 0;
        } else {
            return userRepository.deleteSpecificUser(userId);
        }
    }

    /**
     * get the specified user information.
     * @param userId the user id
     * @return a list of desired format of user information.
     */
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

    /**
     * upgrade the user's authorization
     * @param userId user id
     * @return if the upgrade is successful or not. 1 for yes, 0 for no.
     */
    public int updateUserRole(int userId) {
        if(userId < 0) {
            return 0;
        } else {
            if(userRepository.selectUserTypeById(userId) != 0) {
                return 0;
            } else {
                return userRepository.updateUserRole(1, userId);
            }
        }

    }

    /**
     * check if the user is admin
     * @param userName user name (20 characters limit)
     * @param userKey user's password. (25 characters limit)
     * @return whether the user can access to the admin system.
     */
    public int checkManagerValid(String userName, String userKey) {
        if(userName == null || userKey == null || userKey.length() > 25 || userName.length() > 20) {
            return 0;
        } else {
            List<User> uList = userRepository.selectUserAll(userName);
            if (uList.size() == 0) {
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
    }

    /**
     * modify the user's key.
     * @param oldUserKey the origin key. (25 characters limit)
     * @param userName user's name. (20 characters limit)
     * @param newUserKey new key. (25 characters limit)
     * @return whether the modification operation is successful.
     */
    public int changeUserKey(String oldUserKey, String userName, String newUserKey) {
        if (oldUserKey == null || userName == null || newUserKey == null || oldUserKey.length() > 25 || userName.length() > 20 || newUserKey.length() > 25) {
                return 0;
        } else {
            String orginKey = userRepository.searchUserKeyByName(userName);
            if (!"".equals(orginKey)) {
                if (oldUserKey.equals(orginKey)) {
                    return userRepository.updateUserKey(newUserKey, userName);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
    }
    
}
