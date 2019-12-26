package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import com.proj.meethere.service.UserMessageService;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @Author Tresaresa
 * @Date 2019/12/26 19:44
 */
@SpringBootTest
public class UserMessageServiceTest {

    @Autowired
    private UserMessageService userMessageService;

    @MockBean
    private MessageRepositroy messageRepositroy;

    static private final int msgId = 1;
    static private final String msgContent = "留言内容";
    static private final int msgVisibility = 0;
    static private final String msgTime = "2019-10-29 20:01:11";
    static private final int userId = 3;
    static private final String msgTitle = "标题";

    static private Message message;
    static private List<Message> messages = new ArrayList<>();

    @BeforeAll
    static void init() {
        message = new Message(msgId, msgContent, msgVisibility, msgTime, userId, msgTitle);
        messages.add(message);
    }

    @Test
    void should_return_partial_message() {
        int count = 1;

        // stubbing
        when(messageRepositroy.findLatestMessages(count)).thenReturn(messages);

        // calling
        List<Message> result = userMessageService.selectMessagesPartial(count);

        // verifying result
        assertEquals(count, result.size());
        assertEquals(messages, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).findLatestMessages(count);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_partial_message_of_user() {
        int count = 1;

        // stubbing
        when(messageRepositroy.findLatestMessagesById(count, userId)).thenReturn(messages);

        // calling
        List<Message> result = userMessageService.selectMessagesPartialById(count, userId);

        // verifying result
        assertEquals(count, result.size());
        assertEquals(messages, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).findLatestMessagesById(count, userId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_partial_message_before_time() {
        int count = 1;
        String lastTime = "2021-11-12 13:41:01";

        // stubbing
        when(messageRepositroy.findMoreMessagesBefore(lastTime, count)).thenReturn(messages);

        // calling
        List<Message> result = userMessageService.selectMoreMessagesBeforePartial(lastTime, count);

        // verifying result
        assertEquals(count, result.size());
        assertEquals(messages, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).findMoreMessagesBefore(lastTime, count);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_partial_message_before_time_of_user() {
        int count = 1;
        String lastTime = "2017-11-12 13:41:01";

        // stubbing
        when(messageRepositroy.findMoreMessagesBeforeById(lastTime, count, userId)).thenReturn(messages);

        // calling
        List<Message> result = userMessageService.selectMoreMessagesBeforePartialById(lastTime, count, userId);

        // verifying result
        assertEquals(count, result.size());
        assertEquals(messages, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).findMoreMessagesBeforeById(lastTime, count, userId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_1_when_add_message_success() {
        // stubbing
        when(messageRepositroy.insertNewMessage(msgTitle, msgContent, userId)).thenReturn(1);

        // calling
        int result = userMessageService.addNewMessage(msgTitle, msgContent, userId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).insertNewMessage(msgTitle, msgContent, userId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_0_when_add_message_failed() {
        // stubbing
        when(messageRepositroy.insertNewMessage(msgTitle, msgContent, userId)).thenReturn(0);

        // calling
        int result = userMessageService.addNewMessage(msgTitle, msgContent, userId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).insertNewMessage(msgTitle, msgContent, userId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_1_when_delete_message_success() {
        // stubbing
        when(messageRepositroy.deleteMessage(msgId)).thenReturn(1);

        // calling
        int result = userMessageService.deleteMessage(msgId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).deleteMessage(msgId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_0_when_delete_message_failed() {
        // stubbing
        when(messageRepositroy.deleteMessage(msgId)).thenReturn(0);

        // calling
        int result = userMessageService.deleteMessage(msgId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).deleteMessage(msgId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_1_when_update_message_success() {
        // stubbing
        when(messageRepositroy.updateMessageById(msgTitle, msgContent, msgId)).thenReturn(1);

        // calling
        int result = userMessageService.updateMessage(msgTitle, msgContent, msgId);

        // verifying result
        assertEquals(1, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).updateMessageById(msgTitle, msgContent, msgId);

        verifyNoMoreInteractions(messageRepositroy);
    }

    @Test
    void should_return_0_when_update_message_failed() {
        // stubbing
        when(messageRepositroy.updateMessageById(msgTitle, msgContent, msgId)).thenReturn(0);

        // calling
        int result = userMessageService.updateMessage(msgTitle, msgContent, msgId);

        // verifying result
        assertEquals(0, result);

        // verifying invoked times
        verify(messageRepositroy, times(1)).updateMessageById(msgTitle, msgContent, msgId);

        verifyNoMoreInteractions(messageRepositroy);
    }

}
