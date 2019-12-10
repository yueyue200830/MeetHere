package com.proj.meethere.Service;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 22:33
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepositroy messageRepositroy;

    public List<Message> getUnapprovedMessage() {
        return messageRepositroy.selectAllMessage();
    }

    public List<Message> searchSpecificMessage(int id) {
        return messageRepositroy.selectSpecificMessage(id);
    }

    public int approveMessage(int id) {
        return messageRepositroy.setMessageVisibility(id);
    }

}
