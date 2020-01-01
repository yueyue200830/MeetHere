package com.proj.meethere.controller;

import com.proj.meethere.entity.News;
import com.proj.meethere.service.UserNewsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.sql.SQLException;
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
    public String selectAllNews() throws SQLException {
        List<News> newsList = userNewsService.selectAllNews();
        JSONArray jsonArray = new JSONArray();
        for(News news : newsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", news.getId());
            jsonObject.put("newsContent", news.getNewsContent());
            jsonObject.put("newsTitle", news.getNewsTitle());
            jsonObject.put("newsTime", news.getNewsTime());

            Blob blob = news.getNewsPhoto();
            if(blob == null) {
                jsonObject.put("newsPhoto", "");
            } else {
                jsonObject.put("newsPhoto", new String(blob.getBytes((long)1, (int)blob.length())));
            }

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    /**
     * @return 时间倒序，返回最新的3条新闻
     */
    @RequestMapping(value = "/getNewsThree", method = RequestMethod.POST)
    @ResponseBody
    public String getNewsThree() throws SQLException {
        List<News> newsList = userNewsService.selectNewsPartial(3);

        JSONArray jsonArray = new JSONArray();
        for(News news : newsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", news.getId());
            jsonObject.put("newsContent", news.getNewsContent());
            jsonObject.put("newsTitle", news.getNewsTitle());
            jsonObject.put("newsTime", news.getNewsTime());

            Blob blob = news.getNewsPhoto();
            if(blob == null) {
                jsonObject.put("newsPhoto", "");
            } else {
                jsonObject.put("newsPhoto", new String(blob.getBytes((long)1, (int)blob.length())));
            }

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    /* 可能不用了
    @RequestMapping(value = "/getTenNews", method = RequestMethod.POST)
    @ResponseBody
    public String getTenNewsByPageId(@RequestParam("page_id") int pageId) throws SQLException {
        int start = pageId*10;
        int end = (pageId + 1)*10 - 1;

        List<News> newsList = userNewsService.getTenNewsByPageId(start, end);
        JSONArray jsonArray = new JSONArray();
        for(News news : newsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", news.getId());
            jsonObject.put("newsContent", news.getNewsContent());
            jsonObject.put("newsTitle", news.getNewsTitle());
            jsonObject.put("newsTime", news.getNewsTime());

            Blob blob = news.getNewsPhoto();
            if(blob == null) {
                jsonObject.put("newsPhoto", "");
            } else {
                jsonObject.put("newsPhoto", new String(blob.getBytes((long)1, (int)blob.length())));
            }

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }*/
}
