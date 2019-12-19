package com.proj.meethere;

import com.proj.meethere.service.UserService;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.service.UserService;
import com.proj.meethere.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Yiqing Tao
 * @date 2019-12-17 22:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private List<User> userList;

    @Before
    public void init() {
        User user1 = new User("Jack","passwordIsSecret",1,"mockUserPhoto");
        userList = new ArrayList<>();
        userList.add(user1);
    }
    @After
    public void cleanUp() {
        this.userList.clear();
    }
    @Test
    public void get_user_info_should_be_executed() {
        User user2 = new User("test2","passwd",0,"fakePhoto");
        userList.add(user2);
        when(userRepository.selectAllUserInfo()).thenReturn(userList);
        List<User> users = userService.getUserInfo();
        verify(userRepository,times(1)).selectAllUserInfo();
    }

    @Test
    public void get_specific_user_should_be_executed() {
        when(userRepository.selectSpecificUser(1)).thenReturn(userList);
        List<User> userResult = userService.selectSpecificUserInfo(1);
        verify(userRepository,times(1)).selectSpecificUser(1);
    }

    @Test
    public void delete_specific_user_should_be_executed() {
        when(userRepository.deleteSpecificUser(1)).thenReturn(1);
        int result = userService.deleteSpecificUser(1);
        verify(userRepository,times(1)).deleteSpecificUser(1);
        Assert.assertEquals(1,result);
    }

}
