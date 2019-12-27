package com.proj.meethere.entity;
import javax.persistence.*;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Message Entity
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "msg_content")
    String messageContent;

    /* 0：留言不可见 1：留言可见 */
    @Column(name = "msg_visibility")
    int messageVisibility;

    @Column(name = "msg_time")
    String messageTime;

    @Column(name = "user_id")
    int userId;

    @Column(name = "msg_title")
    String messageTitle;

    public Message() {
    }

    public Message(int id, String content, int visibility, String time, int userId, String title) {
        this.id = id;
        this.messageContent = content;
        this.messageVisibility = visibility;
        this.messageTime = time;
        this.userId = userId;
        this.messageTitle = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getMessageVisibility() {
        return messageVisibility;
    }

    public void setMessageVisibility(int messageVisibility) {
        this.messageVisibility = messageVisibility;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Message(String messageContent, int messageVisibility, String messageTime, int userId, String messageTitle) {
        this.messageContent = messageContent;
        this.messageVisibility = messageVisibility;
        this.messageTime = messageTime;
        this.userId = userId;
        this.messageTitle = messageTitle;
    }
}
