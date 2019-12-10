package com.proj.meethere.controller;

import com.proj.meethere.Service.MessageService;
import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Message Controller
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getUnapproveMessage",method = RequestMethod.POST)
    @ResponseBody
    List<Message> getUnapprovedMessage() {
        return messageService.getUnapprovedMessage();
    }

    /* search specitic message by id */
    @RequestMapping(value = "/searchUnapproveMessage/{condition}",method = RequestMethod.GET)
    @ResponseBody
    List<Message> searchSpecificMessage(@PathVariable String condition) {
        return messageService.searchSpecificMessage(Integer.parseInt(condition));
    }

    @RequestMapping(value = "/approveMessage/{temp}", method = RequestMethod.GET)
    @ResponseBody
    int updateCheckStatus(@PathVariable String temp) {
       return messageService.approveMessage(Integer.parseInt(temp));
    }

}
