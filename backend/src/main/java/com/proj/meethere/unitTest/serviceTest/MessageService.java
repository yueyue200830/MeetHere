package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(id < 0 ){
            return new ArrayList<Message>();
        } else {
            return messageRepositroy.selectSpecificMessage(id);
        }

    }

    public int approveMessage(int id) {
        if(id < 0) {
            return 0;
        } else {
            return messageRepositroy.setMessageVisibility(id);
        }
    }

}
