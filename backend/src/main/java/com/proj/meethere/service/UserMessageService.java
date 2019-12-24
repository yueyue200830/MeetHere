package com.proj.meethere.service;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:50
 */
@Service
public class UserMessageService {

    @Autowired
    private MessageRepositroy messageRepositroy;

    public List<Message> selectMessagesPartial(int count) {
        return messageRepositroy.findLatestMessages(count);
    }

    public List<Message> selectMessagesPartialById(int count, int id) {
        return messageRepositroy.findLatestMessagesById(count, id);
    }

    public List<Message> selectMoreMessagesBeforePartial(String lastTime, int count) {
        return messageRepositroy.findMoreMessagesBefore(lastTime, count);
    }

    public List<Message> selectMoreMessagesBeforePartialById(String lastTime, int count, int id) {
        return messageRepositroy.findMoreMessagesBeforeById(lastTime, count, id);
    }

    public int addNewMessage(String title, String content, int userId) {
        return messageRepositroy.insertNewMessage(content, userId, title);
    }

    public int deleteMessage(int id) {
        return messageRepositroy.deleteMessage(id);
    }

    public int updateMessage(String title, String content, int id) {
        return messageRepositroy.updateMessageById(title, content, id);
    }
}
