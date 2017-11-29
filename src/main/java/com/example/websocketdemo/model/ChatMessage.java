package com.example.websocketdemo.model;

/**
 * Created by anurag garg on 28/11/17.
 */
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String response;
    private String desflowTicketLink;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDesflowTicketLink() {
        return desflowTicketLink;
    }

    public void setDesflowTicketLink(String desflowTicketLink) {
        this.desflowTicketLink = desflowTicketLink;
    }

    @Override
    public String toString() {
        return "ChatMessage [type=" + type + ", content=" + content + ", sender=" + sender + ", response=" + response
                + ", desflowTicketLink=" + desflowTicketLink + "]";
    }

}
