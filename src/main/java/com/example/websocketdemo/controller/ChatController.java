package com.example.websocketdemo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.websocketdemo.OnCallService;
import com.example.websocketdemo.SlackMessagingService;
import com.example.websocketdemo.model.AwsLexServiceResponse;
import com.example.websocketdemo.model.ChatMessage;
import com.hackathon.aws.lex.serivce.AwsLexRestService;

/**
 * Created by anurag garg on 28/11/17.
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private static AwsLexRestService awsLexRestService = new AwsLexRestService();

    private static OnCallService onCallService=new OnCallService();

    private static SlackMessagingService slackMessagingService=new SlackMessagingService();

    @MessageMapping("/chat.sendMessage")
    @SendTo("/channel/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
        logger.info("chat message {}", chatMessage);
        try {
            //provide upload functionality in chatmessage
            AwsLexServiceResponse response = awsLexRestService.getAwsLexBotResponse(chatMessage.getContent());
            chatMessage.setResponse(response.getMessage());
            if("ReadyForFulfillment".equalsIgnoreCase(response.getDialogState())) {
                String OncallUser=onCallService.getOnCallByTeamName(response.getIntentName());
                //create two desflow tickets
                chatMessage.setDesflowTicketLink("http://desflow.ia55.net/Q/ArcHelp/341539");
                slackMessagingService.postMessageToSlack(OncallUser);
            }
        } catch (JSONException e) {
            logger.info("some error occurred while processing request {}",e);
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/channel/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
