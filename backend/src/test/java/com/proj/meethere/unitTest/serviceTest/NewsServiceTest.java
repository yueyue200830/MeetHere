package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import com.proj.meethere.request.NewsRequest;
import com.proj.meethere.service.NewsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Yiqing Tao
 * @date 2019-12-26 22:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {
    @Autowired
    NewsService newsService;

    @MockBean
    NewsRepository newsRepository;

    News news;
    @Before
    public void init() {
        News news = mock(News.class);
    }
    @Test
    public void should_get_all_news() throws Exception {
        List<News> newsList = new ArrayList<>();
        News news2 = mock(News.class);
        newsList.add(news);
        newsList.add(news2);
        when(newsRepository.selectAllNews()).thenReturn(newsList);
        List<NewsRequest> result = newsService.getNews();
        Assert.assertEquals(2, result.size());
        verify(newsRepository, times(1)).selectAllNews();
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void should_select_specific_news() throws Exception {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.selectSpecificNews(1)).thenReturn(newsList);
        List<NewsRequest> result = newsService.searchSpecificNews(1);
        Assert.assertEquals(1, result.size());
        verify(newsRepository, times(1)).selectSpecificNews(1);
        verifyNoMoreInteractions(newsRepository);
    }
    @Test
    public void no_news_should_be_selected_if_id_below_0() {
        //List<NewsRequest> newsRequestList =
    }

    @Test
    public void no_news_should_be_deleted_if_id_below_0() {

    }
    @Test
    public void should_delete_news() throws Exception {
        when(newsRepository.deleteSpecificNews(1)).thenReturn(1);
        int result = newsService.deleteNews(1);
        Assert.assertEquals(1, result);
        verify(newsRepository, times(1)).deleteSpecificNews(1);
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void should_update_news() throws Exception {
        when(newsRepository.updateSpeceficNews("content", "title", "photo", 1)).thenReturn(1);
        int result = newsService.updateNews("content", "title", "photo", 1);
        Assert.assertEquals(1, result);
        verify(newsRepository, times(1)).updateSpeceficNews("content", "title", "photo", 1);
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void should_add_news() {
        when(newsRepository.insertNews("content", "photo", "title")).thenReturn(1);
        int result = newsService.addNews("content", "title", "photo");
        Assert.assertEquals(1, result);
        verify(newsRepository, times(1)).insertNews("content", "photo", "title");
        verifyNoMoreInteractions(newsRepository);
    }


    @Test
    public void should_get_photo_by_id() throws Exception {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.selectSpecificNews(1)).thenReturn(newsList);
        String photo = newsService.getPhotoById(1);
        verify(newsRepository, times(1)).selectSpecificNews(1);
        verifyNoMoreInteractions(newsRepository);
    }

}
