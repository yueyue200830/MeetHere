package com.proj.meethere.service;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:51
 */
@Service
public class UserNewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> selectAllNews() {
        return newsRepository.selectAllNews();
    }

    public List<News> getNewsPartial(int count) {
        List<News> newsList = newsRepository.selectAllNews();
        return newsList.subList(0, count);
    }

    public List<News> getTenNewsByPageId(int start, int end) {
        return newsRepository.findNewsByScope(start, end);
    }
}
