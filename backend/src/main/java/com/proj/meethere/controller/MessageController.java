package com.proj.meethere.controller;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    List<Message> getUnapprovedMessage() {
        List<Message> messagesList = messageRepositroy.selectAllMessage();
        return messagesList;
    }

    /* search specitic message by id */
    @RequestMapping(value = "/searchUnapproveMessage/{condition}",method = RequestMethod.GET)
    @ResponseBody
    List<Message> searchSpecificMessage(@PathVariable String condition) {
        List<Message> msgList = messageRepositroy.selectSpecificMessage(Integer.parseInt(condition));
        return msgList;
    }

    @RequestMapping(value = "/approveMessage/{temp}", method = RequestMethod.GET)
    @ResponseBody
    int updateCheckStatus(@PathVariable String temp) {
        return messageRepositroy.setMessageVisibility(Integer.parseInt(temp));
    }

}
