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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private User user1;
    private Blob blob;

    @Before
    public void init() throws Exception {
        blob = new SerialBlob("1010101".getBytes());
        user1 = new User(1,"Jack","passwordIsSecret",1,blob);
        userList = new ArrayList<>();
        userList.add(user1);
    }

    @After
    public void cleanUp() {
        this.userList.clear();
    }

    @Test
    public void get_user_info_should_be_executed() throws Exception {
        Blob blob2 = new SerialBlob("101110101".getBytes());
        User user2 = new User(2,"test2","passwd",0,blob2);
        userList.add(user2);
        when(userRepository.selectAllUserInfo()).thenReturn(userList);
        List<UserResponse> users = userService.getUserInfo();
        assertAll(()->assertEquals(2, users.size()));
        verify(userRepository,times(1)).selectAllUserInfo();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void get_specific_user_should_be_executed() {
        when(userRepository.selectSpecificUser(1)).thenReturn(userList);
        List<UserResponse> userResult = userService.selectSpecificUserInfo(1);
        assertAll(()->assertEquals(1, userResult.size()));
        verify(userRepository,times(1)).selectSpecificUser(1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_return_0_if_userName_does_not_exist() {
        when(userRepository.selectUserAll("no such name")).thenReturn(new ArrayList<User>());
        int result = userService.checkManagerValid("no such name", "key meaningless");
        assertAll(()->assertEquals(0, result));
        verify(userRepository, times(1)).selectUserAll("no such name");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_return_empty_string_if_userName_does_not_exist() {
        when(userRepository.searchUserKeyByName("no such name")).thenReturn("");
        int result = userService.changeUserKey("origin key", "no such name", "new key");
        assertAll(()->assertEquals(0, result));
        verify(userRepository, times(1)).searchUserKeyByName("no such name");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_return_0_if_type_is_wrong() {
        List <User> uList = new ArrayList<>();
        uList.add(user1);
        when(userRepository.selectUserAll("name exists")).thenReturn(uList);
        when(userRepository.selectUserType("name exists")).thenReturn(0);
        int result = userService.checkManagerValid("name exists", "key doesn't matter");
        assertAll(()->assertEquals(0, result));
        verify(userRepository, times(1)).selectUserAll("name exists");
        verify(userRepository, times(1)).selectUserType("name exists");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_return_0_if_passwd_is_wrong() {
        List <User> uList = new ArrayList<>();
        uList.add(user1);
        when(userRepository.selectUserAll("name exists")).thenReturn(uList);
        when(userRepository.selectUserType("name exists")).thenReturn(1);
        when(userRepository.searchUserKeyByName("name exists")).thenReturn("password ok");
        int result = userService.checkManagerValid("name exists", "password ok");
        assertAll(()->assertEquals(1, result));
        verify(userRepository, times(1)).selectUserAll("name exists");
        verify(userRepository, times(1)).selectUserType("name exists");
        verify(userRepository, times(1)).searchUserKeyByName("name exists");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void delete_specific_user_should_be_executed() {
        when(userRepository.deleteSpecificUser(1)).thenReturn(1);
        int result = userService.deleteSpecificUser(1);
        assertAll(()->assertEquals(1,result));
        verify(userRepository,times(1)).deleteSpecificUser(1);
        verifyNoMoreInteractions(userRepository);
    }

}
