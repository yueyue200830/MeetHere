package com.proj.meethere.service;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import com.proj.meethere.service.MessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Yiqing Tao
 * @date 2019-12-26 23:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {
    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepositroy messageRepositroy;

    Message message;
    @Before
    public void init() {
       message = mock(Message.class);
    }


    @Test
    public void should_get_unapproved_message() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
        when(messageRepositroy.selectAllMessage()).thenReturn(messageList);
        List<Message> result = messageService.getUnapprovedMessage();
        Assert.assertEquals(1,result.size());
        verify(messageRepositroy, times(1)).selectAllMessage();
        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    public void should_approve_message() {
        when(messageRepositroy.setMessageVisibility(1)).thenReturn(1);
        int result = messageService.approveMessage(1);
        Assert.assertEquals(1, result);
        verify(messageRepositroy, times(1)).setMessageVisibility(1);
        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    public void should_get_0_if_id_below_0() {
        int result = messageService.approveMessage(-1);
        Assert.assertEquals(0, result);
    }
    @Test
    public void should_get_empty_list_if_id_below_0() {
        List<Message> result = messageService.searchSpecificMessage(-1);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void should_select_specific_message() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
        when(messageRepositroy.selectSpecificMessage(1)).thenReturn(messageList);
        List<Message> result = messageService.searchSpecificMessage(1);
        Assert.assertEquals(1, result.size());
        verify(messageRepositroy, times(1)).selectSpecificMessage(1);
        verifyNoMoreInteractions(messageRepositroy);
    }
}
