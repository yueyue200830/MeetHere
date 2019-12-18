package com.proj.meethere.controller;

import com.proj.meethere.entity.News;
import com.proj.meethere.service.UserNewsService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019-12-02
 * User News Controller
 */
@Controller
@CrossOrigin(origins = "*")
public class UserNewsController {

    @Autowired
    private UserNewsService userNewsService;

    /**
     * @return 时间倒序，返回所有新闻
     */
    @RequestMapping(value = "/getNewsUserPage", method = RequestMethod.POST)
    @ResponseBody
    public String selectAllNews() {
        List<News> newsList = userNewsService.selectAllNews();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(newsList);
        return jsonArray.toString();
    }

    /**
     * @return 时间倒序，返回最新的3条新闻
     */
    @RequestMapping(value = "/getNewsThree", method = RequestMethod.POST)
    @ResponseBody
    public String getNewsThree() {
        List<News> newsList = userNewsService.getNewsPartial(3);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(newsList);
        return jsonArray.toString();
    }

    /**
     * @param pageId 页码
     * @return 范围(page_id*10, (page_id+1)*10-1)中的十条新闻
     */
    @RequestMapping(value = "/getTenNews", method = RequestMethod.POST)
    @ResponseBody
    public String getTenNewsByPageId(@RequestParam("page_id") int pageId) {
        int start = pageId*10;
        int end = (pageId + 1)*10 - 1;

        List<News> newsList = userNewsService.getTenNewsByPageId(start, end);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(newsList);
        return jsonArray.toString();
    }
}
