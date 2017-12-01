package com.chat.repository;

import java.util.List;

import com.chat.websocketdemo.model.DBChatMessage;

public interface IChatHistoryDAO {

    List<DBChatMessage> findMessagesByUserName(String username);

    DBChatMessage saveChatMessage(DBChatMessage dBChatMessage);

}
