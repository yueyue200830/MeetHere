package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.response.UserResponse;
import com.proj.meethere.service.UserService;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
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
    public void init() throws Exception {
        Blob blob = new SerialBlob("1010101".getBytes());
        User user1 = new User("Jack","passwordIsSecret",1,blob);
        userList = new ArrayList<>();
        userList.add(user1);
    }
    @After
    public void cleanUp() {
        this.userList.clear();
    }

    @Ignore
    @Test
    public void get_user_info_should_be_executed() throws Exception {
        Blob blob2 = new SerialBlob("101110101".getBytes());
        User user2 = new User("test2","passwd",0,blob2);
        userList.add(user2);
        when(userRepository.selectAllUserInfo()).thenReturn(userList);
        List<UserResponse> users = userService.getUserInfo();
        verify(userRepository,times(1)).selectAllUserInfo();
    }

    @Test
    public void get_specific_user_should_be_executed() {
        when(userRepository.selectSpecificUser(1)).thenReturn(userList);
        List<UserResponse> userResult = userService.selectSpecificUserInfo(1);
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
