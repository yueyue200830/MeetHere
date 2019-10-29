package com.proj.meethere.controller;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private NewsRepository newsRepository;

    @RequestMapping(value = "/getNews",method = RequestMethod.POST)
    @ResponseBody
    List<String> getAllNews() {
        List<News> newsList = newsRepository.selectAllNews();
        List<String> result = new ArrayList<>();
        for(News news:newsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",news.getId());
            jsonObject.put("title",news.getNewsTitle());
            jsonObject.put("content",news.getNewsContent());
            result.add(jsonObject.toString());
        }
        return result;
    }

    @RequestMapping(value = "/deleteNews", method = RequestMethod.DELETE)
    @ResponseBody
    int deleteNews(@RequestParam(name = "id") int id) {
        return newsRepository.deleteSpecificNews(id);
    }
//
//    @RequestMapping(value = "/modifyNews", method = RequestMethod.POST)
//    @ResponseBody
//    int updateNews(@RequestParam(name = "id") int id, @RequestParam(name = "content") String content,@RequestParam(name = "title") String title,
//                   @RequestParam(name = "photo"))

    @RequestMapping(value = "/searchNews",method = RequestMethod.GET)
    @ResponseBody
    String getSpecificNews(@RequestParam(name = "id") int id) {
        List<News> newsList = newsRepository.selectSpecificNews(id);
        String result = "";
        if(newsList.size() == 1) {
            News news = newsList.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",news.getId());
            jsonObject.put("content",news.getNewsContent());
            jsonObject.put("title",news.getNewsTitle());
            jsonObject.put("photo",news.getNewsPhoto());
            result = jsonObject.toString();
        } else{
            result = "duplicate";
        }
        return result;
    }

}
