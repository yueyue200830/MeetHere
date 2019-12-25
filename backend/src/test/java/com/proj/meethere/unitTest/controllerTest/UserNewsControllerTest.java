package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.controller.UserNewsController;
import com.proj.meethere.entity.News;
import com.proj.meethere.service.UserNewsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Tresaresa
 * @Date 2019/12/20 17:00
 */
@WebMvcTest(UserNewsController.class)
class UserNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserNewsService userNewsService;

    @BeforeEach
    void init() {
        List<News> newsList = new ArrayList<>();
        given(userNewsService.selectAllNews()).willReturn(newsList);
    }

    @Test
    void should_return_all_news_info() throws Exception {
        mockMvc.perform(post("/getNewsUserPage")).andExpect(status().isOk());
        verify(userNewsService, times(1)).selectAllNews();
    }

    @Test
    void should_return_three_news_info() throws Exception {
        mockMvc.perform(post("/getNewsThree")).andExpect(status().isOk());
        verify(userNewsService, times(1)).selectNewsPartial(3);
    }
}