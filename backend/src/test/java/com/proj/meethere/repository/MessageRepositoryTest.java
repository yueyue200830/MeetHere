package com.proj.meethere.repository;

import com.proj.meethere.dao.MessageRepositroy;
import com.proj.meethere.entity.Message;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-26 21:16
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class MessageRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    MessageRepositroy messageRepositroy;

    private Message message;
    @Before
    public void init() {
        message = new Message("message content", 0, "2019-11-20", 1, "message title");
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
    }

    @Test
    public void message_should_be_all_selected() {
        Message message1 = new Message("content", 0, "2019-11-21", 2, "title again");
        this.testEntityManager.persist(message);
        this.testEntityManager.persist(message1);
        List<Message> messageList = messageRepositroy.selectAllMessage();
        Assert.assertEquals(2, messageList.size());
        Message m1 = messageList.get(0);
        Message m2 = messageList.get(1);
        Assert.assertSame(message, m1);
        Assert.assertSame(message1, m2);
    }

    @Test
    public void specific_message_should_be_selected() {
        this.testEntityManager.persist(message);
        List<Message> messageList = messageRepositroy.selectSpecificMessage(message.getId());
        Assert.assertEquals(1, messageList.size());
        Assert.assertSame(message, messageList.get(0));
    }

    @Test
    public void message_visibility_should_be_set() {
        this.testEntityManager.persist(message);
        List<Message> messageList1 = messageRepositroy.selectSpecificMessage(message.getId());
        Assert.assertEquals(1, messageList1.size());
        messageRepositroy.setMessageVisibility(message.getId());
        List<Message> messageList = messageRepositroy.selectAllMessage();
        Assert.assertEquals(0, messageList.size());
    }

    @Ignore
    @Test
    public void news_should_be_inserted() {
        int result = messageRepositroy.insertNewMessage("message title","message content", 1);
        Assert.assertEquals(1, result);
    }
}
