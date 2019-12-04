package com.proj.meethere.controller;

import com.proj.meethere.dao.NewsRepository;
import com.proj.meethere.entity.News;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private NewsRepository newsRepository;

    @RequestMapping(value = "/getNewsUserPage", method = RequestMethod.POST)
    @ResponseBody
    public String getAllNews() {
        List<News> newsList = newsRepository.selectAllNews();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(newsList);
        return jsonArray.toString();
    }

    // 接受页码
    // 返回10条
}
