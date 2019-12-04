package com.proj.meethere.controller;

import com.proj.meethere.dao.MessageRepositroy;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Message;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019-12-02
 * User Message Controller
 */
@Controller
@CrossOrigin(origins = "*")
public class UserMessageController {
    @Autowired
    private MessageRepositroy messageRepositroy;

    @RequestMapping(value = "/getLatestMessage", method = RequestMethod.POST)
    @ResponseBody
    public String getLatestMessages(int number) {
        List<Message> latestMessages = messageRepositroy.findLatestMessages(number);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(latestMessages);

        return jsonArray.toString();
    }

    @RequestMapping(value = "/getMoreMessage", method = RequestMethod.POST)
    @ResponseBody
    public String getMoreMessages(Date lastTime, int number) {
        List<Message> moreMessages = messageRepositroy.findMoreCMessagesBefore(lastTime, number);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(moreMessages);

        return jsonArray.toString();
    }

}
