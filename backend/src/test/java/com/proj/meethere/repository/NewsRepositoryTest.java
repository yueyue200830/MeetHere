package com.proj.meethere.repository;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

/**
 * @author Yiqing Tao
 * @date 2019-12-20 8:37
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class NewsRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    NewsRepository newsRepository;

    News mockNews;
    Blob mockBlob;
    @Before
    public void init() throws SQLException {
        mockBlob = new SerialBlob("1010101".getBytes());
        mockNews = new News("test News content",  mockBlob, "mock title", "2019-10-29");
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
    }


    @Test
    public void news_should_be_deleted() {
        this.testEntityManager.persist(mockNews);
        int result = newsRepository.deleteSpecificNews(mockNews.getId());
        Assert.assertEquals(1, result);
        List<News> selectedNews = newsRepository.selectSpecificNews(mockNews.getId());
        Assert.assertEquals(0, selectedNews.size());
    }

    @Ignore
    @Test
    public void news_should_be_inserted() {
        int result = newsRepository.insertNews("newsContent", "12312","title");
        Assert.assertEquals(1, result);
        List<News> newsList = newsRepository.selectAllNews();
        Assert.assertEquals(1, newsList.size());

    }
    @Test
    public void all_news_should_be_selected() {
        News news2 = new News("mock news again",  mockBlob, "news content2","2019-10-21");
        this.testEntityManager.persist(mockNews);
        this.testEntityManager.persist(news2);
        List<News> newsList = newsRepository.selectAllNews();
        Assert.assertEquals(2, newsList.size());
        News firstNews = newsList.get(0);
        News secondNews = newsList.get(1);
        Assert.assertSame(mockNews, firstNews);
        Assert.assertSame(news2, secondNews);
    }

    @Test
    public void specific_news_should_be_selected() {
        this.testEntityManager.persist(mockNews);
        List<News> newsList = newsRepository.selectSpecificNews(mockNews.getId());
        Assert.assertEquals(1, newsList.size());
        Assert.assertSame(mockNews, newsList.get(0));
    }

    @Ignore
    @Test
    public void news_should_be_updated() throws Exception {
        System.out.println(mockNews.getId());
        this.testEntityManager.persist(mockNews);
        System.out.println(mockNews.getId());
        Blob newsPhoto = new SerialBlob("10101".getBytes());
        String news = new String(newsPhoto.getBytes(1, (int) newsPhoto.length()), "GBK");
        int result = newsRepository.updateSpeceficNews("change News content","change news Title", news, mockNews.getId());
        Assert.assertEquals(1, result);
        System.out.println(mockNews.getId());
        List<News> newsFind = newsRepository.selectSpecificNews(mockNews.getId());
        Assert.assertEquals(1, newsFind.size());
        News currNews = newsFind.get(0);
        System.out.println(currNews.getId());
        Assert.assertEquals("change News content", currNews.getNewsContent());
        Assert.assertEquals("change news Title", currNews.getNewsTitle());
       // Assert.assertEquals(blob2, currNews.getNewsPhoto());
    }


}
