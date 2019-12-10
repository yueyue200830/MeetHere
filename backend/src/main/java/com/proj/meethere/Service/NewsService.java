package com.proj.meethere.Service;

import com.proj.meethere.Request.NewsRequest;
import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 22:20
 */
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<NewsRequest> getNews() {
        List<News> newsList = newsRepository.selectAllNews();
        List<NewsRequest> newsRequestList = new ArrayList<>();
        for(News news : newsList) {
            NewsRequest newsRequest = new NewsRequest();
            newsRequest.setNewsPhoto(news.getNewsPhoto());
            newsRequest.setNewsContent(news.getNewsContent());
            newsRequest.setId(news.getId());
            newsRequest.setNewsTitle(news.getNewsTitle());
            newsRequestList.add(newsRequest);
        }
        return newsRequestList;
    }

    public int deleteNews(int id) {
        return newsRepository.deleteSpecificNews(id);
    }

    public int updateNews(String content, String title, String photo, int id) {
        return newsRepository.updateSpeceficNews(content, title, photo, id);
    }

    public List<NewsRequest> searchSpecificNews(int id) {
        List<News> newsList = newsRepository.selectSpecificNews(id);
        System.out.println(newsList.get(0).getId());
        List<NewsRequest> newsRequestList = new ArrayList<>();
        for(News news : newsList) {
            NewsRequest newsRequest = new NewsRequest();
            newsRequest.setId(news.getId());
            newsRequest.setNewsContent(news.getNewsContent());
            newsRequest.setNewsPhoto(news.getNewsPhoto());
            newsRequest.setNewsTitle(news.getNewsTitle());
            newsRequestList.add(newsRequest);
        }
        return newsRequestList;
    }

    public int addNews(String content, String title, String photo) {
        return newsRepository.insertNewNews(content, title, photo);
    }

}
