package com.proj.meethere.controller;

import com.proj.meethere.entity.News;
import com.proj.meethere.service.UserNewsService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
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

    static private final int newsId = 1;
    static private final String newsContent = "内容！";
    static private final String newsTitle = "标题";
    static private final String newsTime = "2018-12-21";
    static private News news1;
    static private News news2;
    static private List<News> newses = new ArrayList<>();

    @BeforeAll
    static void init() throws SQLException {
        news1 = new News(newsId, newsContent, null, newsTitle, newsTime);
        news2 = new News(newsId, newsContent, new SerialBlob("1111".getBytes()), newsTitle, newsTime);
        newses.add(news1);
        newses.add(news2);
    }

    @Test
    void should_return_all_news_info() throws Exception {
        given(userNewsService.selectAllNews()).willReturn(newses);
        mockMvc.perform(post("/getNewsUserPage")).andExpect(status().isOk());
        verify(userNewsService, times(1)).selectAllNews();
    }

    @Test
    void should_return_three_news_info() throws Exception {
        given(userNewsService.selectNewsPartial(anyInt())).willReturn(newses);
        mockMvc.perform(post("/getNewsThree")).andExpect(status().isOk());
        verify(userNewsService, times(1)).selectNewsPartial(3);
    }
}