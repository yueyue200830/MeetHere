package com.proj.meethere.controller;

import com.proj.meethere.dao.MessageRepositroy;

import com.proj.meethere.entity.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    public String getLatestMessages() {
        List<Message> latestMessages = messageRepositroy.findLatestMessages(10);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(latestMessages);

        return jsonArray.toString();
    }

    @RequestMapping(value = "/getMoreMessage", method = RequestMethod.GET)
    @ResponseBody
    public String getMoreMessages(@RequestParam("lastTime") String lastTime, @RequestParam("number") int number) {
        List<Message> moreMessages = messageRepositroy.findMoreMessagesBefore(lastTime, number);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(moreMessages);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/addMessage", method = RequestMethod.GET)
    @ResponseBody
    public int addNewMessage(@RequestParam("addMessageForm") String newForm, @RequestParam("id") int user_id) {
        JSONObject jsonObject = new JSONObject(newForm);
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        int ret = messageRepositroy.insertNewMessage(content, user_id, title);
        return ret;
    }
}
