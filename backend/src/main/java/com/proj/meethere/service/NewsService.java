package com.proj.meethere.service;

import com.proj.meethere.request.NewsRequest;
import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 22:20
 */
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<NewsRequest> getNews() throws SQLException, UnsupportedEncodingException {
        List<News> newsList = newsRepository.selectAllNews();
        List<NewsRequest> newsRequestList = new ArrayList<>();
        for(News news : newsList) {
            NewsRequest newsRequest = new NewsRequest();
            if(news.getNewsPhoto() != null) {
                String newsPhoto = new String(news.getNewsPhoto().getBytes(1, (int) news.getNewsPhoto().length()), "GBK");
                newsRequest.setNewsPhoto(newsPhoto);
            }
            else{
                newsRequest.setNewsPhoto(null);
            }
            newsRequest.setNewsContent(news.getNewsContent());
            newsRequest.setId(news.getId());
            newsRequest.setNewsTitle(news.getNewsTitle());
            newsRequestList.add(newsRequest);
        }
        return newsRequestList;
    }

    public int deleteNews(int id) {
        if(id < 0) {
            return 0;
        } else {
            return newsRepository.deleteSpecificNews(id);
        }
    }

    public int updateNews(String content, String title, String photo, int id) {
        if(id < 0) {
            return 1;
        } else {
            return newsRepository.updateSpeceficNews(content, title, photo, id);
        }
    }

    public List<NewsRequest> searchSpecificNews(int id) throws SQLException, UnsupportedEncodingException {
        if(id < 0 ){
            return new ArrayList<NewsRequest>();
        } else {
            List<News> newsList = newsRepository.selectSpecificNews(id);
            System.out.println(newsList.get(0).getId());
            List<NewsRequest> newsRequestList = new ArrayList<>();
            for (News news : newsList) {
                NewsRequest newsRequest = new NewsRequest();
                newsRequest.setId(news.getId());
                newsRequest.setNewsContent(news.getNewsContent());
                String newsPhoto = new String(news.getNewsPhoto().getBytes(1, (int) news.getNewsPhoto().length()), "GBK");
                newsRequest.setNewsPhoto(newsPhoto);
                newsRequest.setNewsTitle(news.getNewsTitle());
                newsRequestList.add(newsRequest);
            }
            return newsRequestList;
        }
    }

    public int addNews(String content, String title, String photo) {
        try {
            System.out.println(content);
             return newsRepository.insertNews(content, photo, title);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return 0;
    }

    public String getPhotoById(int id) throws SQLException, UnsupportedEncodingException {
        if(id < 0) {
            return "";
        } else {
            List<News> newsList = newsRepository.selectSpecificNews(id);
            Blob newsPhoto = newsList.get(0).getNewsPhoto();
            String photo = new String(newsPhoto.getBytes(1, (int) newsPhoto.length()), "GBK");
            return photo;
        }
    }

}
