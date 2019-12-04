package com.proj.meethere.controller;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
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
 * @Author Yiqing Tao, Tresaresa
 * @Date 2019-10-29
 * Message Controller
 */
@Controller
public class MessageController {
    @Autowired
    private MessageRepositroy messageRepositroy;

    @RequestMapping(value = "/getUnapproveMessage",method = RequestMethod.POST)
    @ResponseBody
    List<String> getUnapprovedMessage() {
        List<Message> messagesList = messageRepositroy.selectAllMessage();
        List<String> result = new ArrayList<>();
        for(Message msg:messagesList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",msg.getId());
            jsonObject.put("user_id",msg.getUserId());
            jsonObject.put("msg_title",msg.getMessageTitle());
            jsonObject.put("msg_content",msg.getMessageContent());
            jsonObject.put("msg_visibility",msg.getMessageVisibility());
            result.add(jsonObject.toString());
        }
        return result;
    }

    /* search specitic message by id */
    @RequestMapping(value = "/searchUnapproveMessage",method = RequestMethod.POST)
    @ResponseBody
    String searchSpecificMessage(@RequestParam(name = "id") int id) {
        List<Message> msgList = messageRepositroy.selectSpecificMessage(id);
        String result = "";
        if(msgList.size() == 1) {
            Message msg = msgList.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",msg.getId());
            jsonObject.put("user_id",msg.getUserId());
            jsonObject.put("msg_title",msg.getMessageTitle());
            jsonObject.put("msg_content",msg.getMessageContent());
            jsonObject.put("msg_visibility",msg.getMessageVisibility());
            result = jsonObject.toString();
        }else {
            result = "duplicate";
        }
        return result;
    }

    @RequestMapping(value = "/approveMessage", method = RequestMethod.POST)
    @ResponseBody
    int updateCheckStatus(@RequestParam(name = "id") int id) {
        return messageRepositroy.setMessageVisibility(id);
    }

}
