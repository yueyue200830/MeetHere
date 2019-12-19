package com.proj.meethere;

import com.proj.meethere.service.*;
import com.proj.meethere.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    MessageService messageService;

    @MockBean
    NewsService newsService;

    @MockBean
    OrderService orderService;

    @MockBean
    RevenueService revenueService;
    UserController userController;
    @Test
    public void should_get_user() throws Exception{
        ResultActions perform = mockMvc.perform(get("/getUser"));
        perform.andExpect(status().isOk());
        verify(userService,times(1)).getUserInfo();
    }

    @Test
    public void should_delete_user() throws Exception{
        ResultActions perform = mockMvc.perform(get("/deleteUser").param("1"));
        perform.andExpect(status().isOk());
        verify(userService, times(1)).deleteSpecificUser(1);

    }

    @Test
    public void should_select_specific_user() throws Exception {
        ResultActions perform = mockMvc.perform(get("/searchUser").param("1"));
        perform.andExpect(status().isOk());
        verify(userService,times(1)).selectSpecificUserInfo(1);
    }

}
