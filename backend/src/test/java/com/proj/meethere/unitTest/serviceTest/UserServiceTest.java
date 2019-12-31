package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.response.UserResponse;
import com.proj.meethere.service.UserService;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import com.proj.meethere.utils.TestUtils;
import org.junit.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static Blob blob = null;
    static final int userId = 1;
    static final String userName = "Jack";
    static final String userKey = "passwordIsSecret";
    static final int userType = 1;
    static {
        try {
            blob = new SerialBlob("10101".getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() throws Exception {
        user1 = new User(1,userName, userKey,userType,blob);
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
    public void should_get_0_if_id_below_0_when_update_user_role() {
        int result = userService.updateUserRole(-1);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_return_0_if_the_id_want_delete_below_0() {
        int result = userService.deleteSpecificUser(-1);
        assertAll(()->assertEquals(0, result));
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

    @Test
    public void user_type_should_be_upgraded() {
        when(userRepository.updateUserRole(1,2)).thenReturn(1);
        int result = userService.updateUserRole(2);
        assertAll(()->assertEquals(1, result));
        verify(userRepository, times(1)).selectUserTypeById(2);
        verify(userRepository, times(1)).updateUserRole(1,2);
        verifyNoMoreInteractions(userRepository);
    }

    @ParameterizedTest
    @MethodSource("provideManageValidSource")
    public void should_return_0_if_user_info_not_valid(String userName, String userKey) {
        int result = userService.checkManagerValid(userName, userKey);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(userRepository);
    }

    @ParameterizedTest
    @MethodSource("provideChangeUserKeySource")
    public void should_return_0_if_user_key_is_not_valid(String oldUserKey, String userName, String newUserKey) {
            int result = userService.changeUserKey(oldUserKey, userName, newUserKey);
            assertAll(()->assertEquals(0, result));
            verifyNoMoreInteractions(userRepository);
    }

    static List<Arguments> provideChangeUserKeySource() {
        return Arrays.asList(Arguments.of(null, "normal name", "normal key"),
                Arguments.of("old key", null, "new key"),
                Arguments.of("old key", "normal name", null),
                Arguments.of(null, null, "new key"),
                Arguments.of(null, "normal name", null),
                Arguments.of("old key", null, null),
                Arguments.of(null, null, null),
                Arguments.of(TestUtils.generateString(17), "normal name", "normal key"),
                Arguments.of("old key", TestUtils.generateString(21), "normal key"),
                Arguments.of("old key", "normal name", TestUtils.generateString(26)),
                Arguments.of(null, TestUtils.generateString(21), null),
                Arguments.of(null, TestUtils.generateString(21), "normal key"),
                Arguments.of("old key", TestUtils.generateString(21), null),
                Arguments.of(TestUtils.generateString(17), null, "normal key"),
                Arguments.of(TestUtils.generateString(17), "normal name", null),
                Arguments.of(TestUtils.generateString(17), null, null),
                Arguments.of(TestUtils.generateString(17), null, TestUtils.generateString(17)),
                Arguments.of(TestUtils.generateString(17), "normal name", TestUtils.generateString(17)),
                Arguments.of("old key", TestUtils.generateString(21),TestUtils.generateString(17)),
                Arguments.of(null, TestUtils.generateString(21),TestUtils.generateString(17)),
                Arguments.of(TestUtils.generateString(17), TestUtils.generateString(21), TestUtils.generateString(17)));
    }

    static List<Arguments> provideManageValidSource() {
        return Arrays.asList(Arguments.of(null, "normal key"),
                Arguments.of("normal name", null),
                Arguments.of(null, null),
                Arguments.of(TestUtils.generateString(21), "normal key"),
                Arguments.of(TestUtils.generateString(21), null),
                Arguments.of("normal name", TestUtils.generateString(26)),
                Arguments.of(null, TestUtils.generateString(26)),
                Arguments.of(TestUtils.generateString(21), TestUtils.generateString(26)));
    }
}
