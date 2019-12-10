package com.proj.meethere.controller;

import com.proj.meethere.Request.NewsAddRequest;
import com.proj.meethere.Request.NewsRequest;
import com.proj.meethere.Service.NewsService;
import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * News Controller
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    @ResponseBody
    List<NewsRequest> getAllNews() {
        return newsService.getNews();
    }


    @RequestMapping(value = "/deleteNews/{id}", method = RequestMethod.GET)
    @ResponseBody
    int deleteNews(@PathVariable String id) {
        return newsService.deleteNews(Integer.parseInt(id));
    }


    @RequestMapping(value = "/modifyNews", method = RequestMethod.POST)
    @ResponseBody
    public int updateNews(@RequestBody NewsRequest newsRequest) {
        return newsService.updateNews(newsRequest.getNewsContent(), newsRequest.getTitle(), newsRequest.getNewsPhoto(), newsRequest.getId());
    }

    @RequestMapping(value = "/searchNews/{id}",method = RequestMethod.GET)
    @ResponseBody
    List<NewsRequest> getSpecificNews(@PathVariable String id) {
        return  newsService.searchSpecificNews(Integer.parseInt(id));
    }

    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    @ResponseBody
    public int addNewNews(@RequestBody NewsAddRequest newsAddRequest) {
        //todo: fix photo string problem.
        return newsService.addNews(newsAddRequest.getNewsContent(), newsAddRequest.getTitle(), newsAddRequest.getNewsPhoto().toString());
 }
}
