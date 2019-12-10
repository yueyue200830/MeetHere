package com.proj.meethere.controller;

import com.proj.meethere.Request.NewsRequest;
import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * News Controller
 */
@Controller
public class NewsController {
    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    @ResponseBody
    List<NewsRequest> getAllNews() {
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

    @RequestMapping(value = "/deleteNews/{id}", method = RequestMethod.GET)
    @ResponseBody
    int deleteNews(@PathVariable String id) {
        return newsRepository.deleteSpecificNews(Integer.parseInt(id));
    }


    @RequestMapping(value = "/modifyNews", method = RequestMethod.POST)
    @ResponseBody
    public int updateNews(@RequestBody NewsRequest newsRequest) {
        return newsRepository.updateSpeceficNews(newsRequest.getNewsContent(), newsRequest.getTitle(), newsRequest.getNewsPhoto(), newsRequest.getId());
    }

    @RequestMapping(value = "/searchNews/{id}",method = RequestMethod.GET)
    @ResponseBody
    List<NewsRequest> getSpecificNews(@PathVariable String id) {
        List<News> newsList = newsRepository.selectSpecificNews(Integer.parseInt(id));
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

//    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
//    @ResponseBody
//    public int addNewNews(@RequestParam("newsContent") String newsContent, @RequestParam("newsTitle") String newsTitle,
//                   @RequestParam("newsPhoto") String newsPhoto){
//        return newsRepository.insertNewNews(newsContent, newsTitle, newsPhoto);
// }
}
