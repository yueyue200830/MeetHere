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

    public List<News> selectNewsPartial(int count) {
        return newsRepository.findNewsPartial(count);
    }
}
