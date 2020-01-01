package com.proj.meethere.controller;

import com.proj.meethere.request.NewsRequest;
import com.proj.meethere.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
    List<NewsRequest> getAllNews() throws UnsupportedEncodingException, SQLException {
        return newsService.getNews();
    }


    @RequestMapping(value = "/deleteNews/{id}", method = RequestMethod.GET)
    @ResponseBody
    int deleteNews(@PathVariable String id) {
        return newsService.deleteNews(Integer.parseInt(id));
    }


    @RequestMapping(value = "/modifyNews", method = RequestMethod.POST)
    @ResponseBody
    public int updateNews(@RequestBody NewsRequest newsRequest) throws SQLException {
//        System.out.println("noe modify news" );
        return newsService.updateNews(newsRequest.getNewsContent(), newsRequest.getNewsTitle(), newsRequest.getNewsPhoto(), newsRequest.getId());
    }

    @RequestMapping(value = "/searchNews/{id}",method = RequestMethod.GET)
    @ResponseBody
    List<NewsRequest> getSpecificNews(@PathVariable String id) throws UnsupportedEncodingException, SQLException {
        return  newsService.searchSpecificNews(Integer.parseInt(id));
    }

    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    @ResponseBody
    public int addNewNews(@RequestBody NewsRequest newsRequest) throws SQLException {
        return newsService.addNews(newsRequest.getNewsContent(), newsRequest.getNewsTitle(), newsRequest.getNewsPhoto());
 }

    @RequestMapping(value = "/getPhoto/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getPhotoById(@PathVariable String id) throws UnsupportedEncodingException, SQLException {
        return newsService.getPhotoById(Integer.parseInt(id));
    }
}
