package com.proj.meethere.controller;

import com.proj.meethere.request.NewsRequest;
import com.proj.meethere.service.NewsService;
import com.alibaba.fastjson.*;
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
 * @date 2019-12-26 13:52
 */
@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
public class NewsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    NewsService newsService;

    @Test
    public void should_get_news() throws Exception {
        ResultActions actions = mockMvc.perform(post("/getNews"));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).getNews();
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void should_delete_news() throws Exception {
        ResultActions actions = mockMvc.perform(get("/deleteNews/{id}", "1"));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).deleteNews(1);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void should_update_news() throws Exception {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setNewsPhoto("news photo");
        newsRequest.setId(1);
        newsRequest.setNewsContent("news content");
        newsRequest.setNewsTitle("news title");
        newsRequest.setDelete(false);
        newsRequest.setTitle("修改新闻");
        String newsString = JSONObject.toJSONString(newsRequest);
        ResultActions actions = mockMvc.perform(post("/modifyNews").contentType(MediaType.APPLICATION_JSON).content(newsString));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).updateNews("news content", "news title", "news photo", 1);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void should_add_news() throws Exception {
        NewsRequest newsRequest = new NewsRequest();
        String newsContent = "news content";
        String newsTitle = "news title";
        String title = "修改新闻";
        String photo = "news photo";
        newsRequest.setNewsTitle(newsTitle);
        newsRequest.setDelete(false);
        newsRequest.setNewsContent(newsContent);
        newsRequest.setId(1);
        newsRequest.setNewsPhoto(photo);
        ResultActions actions = mockMvc.perform(post("/addNews").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(newsRequest)));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).addNews(newsContent, newsTitle, photo);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void should_search_news_by_id() throws Exception {
        ResultActions actions = mockMvc.perform(get("/searchNews/{id}", "1"));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).searchSpecificNews(1);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void should_get_photo_by_id() throws  Exception {
        ResultActions actions = mockMvc.perform(get("/getPhoto/{id}", "1"));
        actions.andExpect(status().isOk());
        verify(newsService, times(1)).getPhotoById(1);
        verifyNoMoreInteractions(newsService);
    }
}
