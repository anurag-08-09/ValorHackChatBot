package com.chat.websocketdemo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.aws.lex.serivce.AwsLexRestService;
import com.chat.websocketdemo.OnCallService;
import com.chat.websocketdemo.SlackMessagingService;
import com.chat.websocketdemo.model.AwsLexServiceResponse;
import com.chat.websocketdemo.model.ChatMessage;
import com.chat.websocketdemo.model.DBChatMessage;

/**
 * Created by anurag garg on 28/11/17.
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private static AwsLexRestService awsLexRestService = new AwsLexRestService();

    private static OnCallService onCallService = new OnCallService();

    private static SlackMessagingService slackMessagingService = new SlackMessagingService();

    //@Autowired
    //IChatHistoryDAO ChatHistoryDAO;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/channel/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
        chatMessage.setTimstamp(new Date());
        logger.info("chat message {}", chatMessage);
        String userName=(String)headerAccessor.getSessionAttributes().get("user-name");
        try {
            // provide upload functionality in chatmessage
            AwsLexServiceResponse response = awsLexRestService.getAwsLexBotResponse(chatMessage.getContent());
            chatMessage.setResponse(response.getMessage());
            if ("ReadyForFulfillment".equalsIgnoreCase(response.getDialogState())) {
                String OncallUser = onCallService.getOnCallByTeamName(response.getIntentName());
                // create two desflow tickets
                chatMessage.setDesflowTicketLink("http://desflow.ia55.net/Q/ArcHelp/341539");
                slackMessagingService.postMessageToSlack(OncallUser);
            }
            DBChatMessage dbChatMessage=new DBChatMessage();
            dbChatMessage.setContent(chatMessage.getContent());
            dbChatMessage.setSender(userName);
            dbChatMessage.setTimestamp(new Date());
            dbChatMessage.setUsername(userName);
            //ChatHistoryDAO.saveChatMessage(dbChatMessage);

        } catch (JSONException e) {
            logger.info("some error occurred while processing request {}", e);
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/channel/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.getChatHistory")
    @SendTo("/channel/public")
    public List<DBChatMessage> getChatHistory(SimpMessageHeaderAccessor headerAccessor) {
        String userName=(String)headerAccessor.getSessionAttributes().get("user-name");
       // List<DBChatMessage> chatMessage=repository.findByUsername(userName);
        return null;
    }

}
