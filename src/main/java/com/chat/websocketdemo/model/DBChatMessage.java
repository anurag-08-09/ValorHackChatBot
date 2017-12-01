package com.chat.websocketdemo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userchat_history")
public class DBChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatmessageId", unique = true, nullable = false)
    private int chatmessageId;
    @Column(name = "username", nullable = true)
    private String username;
    @Column(name = "sender", nullable = true)
    private String sender;
    @Column(name = "content", nullable = true)
    private String content;
    @Column(name = "desflowTicket", nullable = true)
    private String desflowTicket;
    @Column(name = "timestamp", nullable = true)
    private Date timestamp;

    public String getSender() {
        return sender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesflowTicket() {
        return desflowTicket;
    }

    public void setDesflowTicket(String desflowTicket) {
        this.desflowTicket = desflowTicket;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getChatmessageId() {
        return chatmessageId;
    }

    public void setChatmessageId(int chatmessageId) {
        this.chatmessageId = chatmessageId;
    }

}
