package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
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
 * @Date 2019/12/26 19:50
 */
@SpringBootTest
public class UserNewsServiceTest {

    @Autowired
    private UserNewsService userNewsService;

    @MockBean
    private NewsRepository newsRepository;

    static private final int newsId = 1;
    static private final String newsContent = "内容！";
    static private final Blob newsPhoto = null;
    static private final String newsTitle = "标题";
    static private final String newsTime = "2018-12-21";

    static private News news;
    static private List<News> newses = new ArrayList<>();

    @BeforeAll
    static void init() {
        news = new News(newsId, newsContent, newsPhoto, newsTitle, newsTime);
        newses.add(news);
    }

    @Test
    void should_return_all_news() {
        // stubbing
        when(newsRepository.selectAllNews()).thenReturn(newses);

        // calling
        List<News> result = userNewsService.selectAllNews();

        // verifying result
        assertEquals(newses, result);

        // verifying invoked times
        verify(newsRepository, times(1)).selectAllNews();

        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    void should_return_partial_news() {
        int count = 1;

        // stubbing
        when(newsRepository.findNewsPartial(count)).thenReturn(newses);

        // calling
        List<News> result = userNewsService.selectNewsPartial(count);

        // verifying result
        assertEquals(count, result.size());
        assertEquals(newses, result);

        // verifying invoked times
        verify(newsRepository, times(1)).findNewsPartial(count);

        verifyNoMoreInteractions(newsRepository);
    }
}
