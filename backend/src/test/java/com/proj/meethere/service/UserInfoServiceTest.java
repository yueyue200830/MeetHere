package com.proj.meethere.service;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @Author Tresaresa
 * @Date 2019/12/26 11:13
 */
@SpringBootTest
class UserInfoServiceTest {
    
    @Autowired
    private UserInfoService userInfoService;
    
    @MockBean
    private UserRepository userRepository;

    static private final int userId = 1;
    static private final String userName = "user1";
    static private final String userKey = "pass123";
    static private final int userType = 0;
    static private final Blob userPhoto = null;

    static private User user;
    static private List<User> users = new ArrayList<>();

    @BeforeAll
    static void init() {
        user = new User(userId, userName, userKey, userType, userPhoto);
        users.add(user);
    }
    
    @Test
    void should_return_1_when_user_name_exist() {
        // stubbing
        when(userRepository.selectUserByName(userName)).thenReturn(users);

        // calling
        int result = userInfoService.checkUserNameExist(userName);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByName(userName);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_0_when_user_name_not_exist() {
        // stubbing
        when(userRepository.selectUserByName(userName)).thenReturn(new ArrayList<>());

        // calling
        int result = userInfoService.checkUserNameExist(userName);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByName(userName);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_1_when_user_name_exist_and_is_not_mine() {
        // stubbing
        when(userRepository.selectUserByNameAndId(userName, userId)).thenReturn(users);

        // calling
        int result = userInfoService.checkUserNameNew(userName, userId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByNameAndId(userName, userId);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_0_when_user_name_not_exist_and_is_not_mine() {
        // stubbing
        when(userRepository.selectUserByNameAndId(userName, userId)).thenReturn(new ArrayList<>());

        // calling
        int result = userInfoService.checkUserNameNew(userName, userId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByNameAndId(userName, userId);

        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    void should_return_1_when_update_name_success() {
        String newName = "123123pass..";
        // stubbing
        when(userRepository.updateUserNameById(userId, newName)).thenReturn(1);

        // calling
        int result = userInfoService.updateUserNameById(userId, newName);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).updateUserNameById(userId, newName);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_minus1_when_update_key_with_wrong_origionalkey() {
        String newKey = "newkey";
        // stubbing
        when(userRepository.selectUserByIdAndKey(userId, userKey)).thenReturn(new ArrayList<>());

        // calling
        int result = userInfoService.updateUserPassById(userId, newKey, userKey);

        // verifying result
        assertEquals(-1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByIdAndKey(userId, userKey);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_0_when_update_key_failed() {
        String newKey = "newkey";
        // stubbing
        when(userRepository.selectUserByIdAndKey(userId, userKey)).thenReturn(users);
        when(userRepository.updateUserPassById(userId, newKey)).thenReturn(0);

        // calling
        int result = userInfoService.updateUserPassById(userId, newKey, userKey);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByIdAndKey(userId, userKey);
        verify(userRepository, times(1)).updateUserPassById(userId, newKey);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_1_when_update_key_success() {
        String newKey = "newkey";
        // stubbing
        when(userRepository.selectUserByIdAndKey(userId, userKey)).thenReturn(users);
        when(userRepository.updateUserPassById(userId, newKey)).thenReturn(1);

        // calling
        int result = userInfoService.updateUserPassById(userId, newKey, userKey);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByIdAndKey(userId, userKey);
        verify(userRepository, times(1)).updateUserPassById(userId, newKey);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_1_when_insert_success() {
        List<User> users = new ArrayList<>();

        // stubbing
        when(userRepository.insertNewUser(userName, userKey, userType, userPhoto)).thenReturn(1);
        when(userRepository.selectUserByName(userName)).thenReturn(users);
        // calling
        int result = userInfoService.insertNewUser(userName, userKey, userType);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).insertNewUser(userName, userKey, userType, userPhoto);
        verify(userRepository, times(1)).selectUserByName(userName);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_0_when_insert_failed() {
        List<User> users = new ArrayList<>();

        // stubbing
        when(userRepository.insertNewUser(userName, userKey, userType, userPhoto)).thenReturn(0);
        when(userRepository.selectUserByName(userName)).thenReturn(users);

        // calling
        int result = userInfoService.insertNewUser(userName, userKey, userType);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(userRepository, times(1)).insertNewUser(userName, userKey, userType, userPhoto);
        verify(userRepository, times(1)).selectUserByName(userName);

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_minus_1_when_same_name() {
        User user = new User("name", "key", 1, null);
        List<User> users = new ArrayList<>();
        users.add(user);

        // stubbing
        when(userRepository.selectUserByName(userName)).thenReturn(users);

        // calling
        int result = userInfoService.insertNewUser(userName, userKey, userType);

        // verifying result
        assertEquals(-1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByName(userName);

        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    void should_return_id_when_login_success() {
        // stubbing
        when(userRepository.selectUserByNameAndKey(userName, userKey)).thenReturn(users);

        // calling
        int result = userInfoService.loginValidation(userName, userKey);

        // verifying result
        assertEquals(userId, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByNameAndKey(userName, userKey);

        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    void should_return_minus_1_when_login_failed() {
        // stubbing
        when(userRepository.selectUserByNameAndKey(userName, userKey)).thenReturn(new ArrayList<>());

        // calling
        int result = userInfoService.loginValidation(userName, userKey);

        // verifying result
        assertEquals(-1, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectUserByNameAndKey(userName, userKey);

        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    void should_return_1_when_update_photo_success() {
        // stubbing
        when(userRepository.updateUserPhotoById("", userId)).thenReturn(1);

        // calling
        int result = userInfoService.updateUserPhoto("", userId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(userRepository, times(1)).updateUserPhotoById(anyString(), eq(userId));

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_return_0_when_update_photo_failed() {
        // stubbing
        when(userRepository.updateUserPhotoById("", userId)).thenReturn(0);

        // calling
        int result = userInfoService.updateUserPhoto("", userId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(userRepository, times(1)).updateUserPhotoById(anyString(), eq(userId));

        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    void should_return_blob_when_select_user_photo() {
        // stubbing
        when(userRepository.selectSpecificUser(userId)).thenReturn(users);

        // calling
        Blob result = userInfoService.selectUserPhoto(userId);

        // verifying result
        assertEquals(userPhoto, result);

        // verifying invoked times
        verify(userRepository, times(1)).selectSpecificUser(userId);

        verifyNoMoreInteractions(userRepository);
    }
}