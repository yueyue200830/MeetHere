package com.proj.meethere.controller;

import com.proj.meethere.request.UserModifyPassword;
import com.proj.meethere.request.UserRequest;
import com.proj.meethere.service.*;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yiqing Tao
 * @date 2019-12-18 13:23
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    UserController userController;

    @Test
    public void should_modify_manager_passwd() throws Exception {
        UserModifyPassword userModifyPassword = new UserModifyPassword();
        userModifyPassword.setUserName("momo");
        userModifyPassword.setOldPassword("123");
        userModifyPassword.setNewPassword("1234");
        userModifyPassword.setConfirmPassword("1234");
        String requestJson = JSONObject.toJSONString(userModifyPassword);
        ResultActions perform = mockMvc.perform(post("/modifyManagerPassword").contentType(MediaType.APPLICATION_JSON).content(requestJson));
        perform.andExpect(status().isOk());
        verify(userService, times(1)).changeUserKey("123", "momo", "1234");
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void should_change_user_role() throws Exception {
        ResultActions perform= mockMvc.perform(get("/changeUserRole/{id}", "1"));
        perform.andExpect(status().isOk());
        verify(userService,times(1)).updateUserRole(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void should_check_password() throws Exception {
        UserRequest fakeUserRequest = new UserRequest();
        fakeUserRequest.setUser_name("momo");
        fakeUserRequest.setUser_key("1234");
        String jsonString = JSONObject.toJSONString(fakeUserRequest);
        ResultActions perform = mockMvc.perform(post("/managerLoginCheck").contentType(MediaType.APPLICATION_JSON).content(jsonString));
        perform.andExpect(status().isOk());
        verify(userService, times(1)).checkManagerValid("momo","1234");
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void should_get_user() throws Exception{
        ResultActions perform = mockMvc.perform(post("/getUser"));
        perform.andExpect(status().isOk());
        verify(userService,times(1)).getUserInfo();
    }

    @Test
    public void should_delete_user() throws Exception{
        ResultActions perform = mockMvc.perform(get("/deleteUser/{temp}","1"));
        perform.andExpect(status().isOk());
        verify(userService, times(1)).deleteSpecificUser(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void should_select_specific_user() throws Exception {
        ResultActions perform = mockMvc.perform(get("/searchUser/{condition}","1"));
        perform.andExpect(status().isOk());
        verify(userService,times(1)).selectSpecificUserInfo(1);
        verifyNoMoreInteractions(userService);
    }

}
