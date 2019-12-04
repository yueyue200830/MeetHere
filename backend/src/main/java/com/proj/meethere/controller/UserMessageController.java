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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public String getLatestMessages() {
        List<Message> latestMessages = messageRepositroy.findLatestMessages(10);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(latestMessages);

        return jsonArray.toString();
    }

    @RequestMapping(value = "/getMoreMessage", method = RequestMethod.GET)
    @ResponseBody
    public String getMoreMessages(@RequestParam("lastTime") String lastTime, @RequestParam("number") int number) throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date time = isoFormat.parse(lastTime);
        List<Message> moreMessages = messageRepositroy.findMoreCMessagesBefore(time.toString(), number);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(moreMessages);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    @ResponseBody
    int addNewMessage(@RequestParam("content") String content, @RequestParam("user_id") int user_id, @RequestParam("title") String title) {
        int ret = messageRepositroy.insertNewMessage(content, user_id, title);
        System.out.println("add new message " + ret);
        return ret;
    }

}
