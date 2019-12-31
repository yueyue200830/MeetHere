package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import com.proj.meethere.request.NewsRequest;
import com.proj.meethere.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    static final int newsId = 1;
    static final String newsContent = "news content";
    static final String newsTitle = "news title";
    static final String newsTime = "2019-11-20";
    static Blob newsPhoto = null;

    static final int newsId2 = 1;
    static final String newsContent2 = "mock content";
    static final String newsTitle2 = "mock title";
    static final String newsTime2 = "2019-11-21";
    static Blob newsPhoto2 = null;

    static {
        try {
            newsPhoto = new SerialBlob("10101".getBytes());
            newsPhoto2 = new SerialBlob("101011".getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() {
       news = new News(newsId, newsContent, newsPhoto, newsTitle, newsTime);
    }
    @Test
    public void should_get_all_news() throws Exception {
        List<News> newsList = new ArrayList<>();
        News news2 = new News(newsId2, newsContent2, newsPhoto2, newsTitle2, newsTime2);
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
    public void no_news_should_be_selected_if_id_below_0() throws UnsupportedEncodingException, SQLException {
        List<NewsRequest> newsRequestList = newsService.searchSpecificNews(-1);
        assertAll(()->assertEquals(0, newsRequestList.size()));
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void no_news_should_be_deleted_if_id_below_0() {
        int result = newsService.deleteNews(-1);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(newsRepository);
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

    @Test
    public void should_return_empty_when_get_photo_by_id_below_0() throws Exception {
        String result = newsService.getPhotoById(-1);
        assertAll(()->assertEquals("", result));
    }

    @ParameterizedTest
    @MethodSource("provideUpdateExceptionSource")
    public void should_return_0_if_update_content_is_null_or_id_below_0(int id, String newsContent, String newsTitle, String newsPhoto) {
        int result = newsService.updateNews(newsContent, newsTitle, newsPhoto, id);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(newsRepository);
    }

    @ParameterizedTest
    @MethodSource("provideAddExceptionSource")
    public void should_return_0_if_newsContent_is_null_or_title_is_null_when_added(String content, String title,String photo) {
        int result = newsService.addNews(content,title,photo);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(newsRepository);
    }

    static List<Arguments> provideAddExceptionSource() {
        return Arrays.asList(Arguments.of(null,"normal title","photo"),
                Arguments.of("normal content", null, "photo"),
                Arguments.of(null, null, "photo"),
                Arguments.of(TestUtils.generateString(1001),"normal title", "photo"),
                Arguments.of(TestUtils.generateString(1001),null, "photo"),
                Arguments.of("normal content", TestUtils.generateString(51), "photo"),
                Arguments.of(null, TestUtils.generateString(51), "photo"),
                Arguments.of(TestUtils.generateString(1001), TestUtils.generateString(51), "photo"));
    }

    static List<Arguments> provideUpdateExceptionSource() {
        return Arrays.asList(Arguments.of(-1,"normal content","normal title", "photo"),
                Arguments.of(1, null,"normal title", "photo"),
                Arguments.of(1, "normal content", null,"photo"),
                Arguments.of(1, null, null, "photo"),
                Arguments.of(-1, null, "normal title", "photo"),
                Arguments.of(-1, "normal content", null, "photo"),
                Arguments.of(-1, null, null, "photo"));
    }
}
