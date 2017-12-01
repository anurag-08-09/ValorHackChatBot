package com.chat.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chat.websocketdemo.model.DBChatMessage;

@Transactional
@Repository("productCatalogDaoImpl")
public class ChatHistoryDAO implements IChatHistoryDAO {

    @Autowired
    ChatHistoryRepository chatHistoryRepository;

    @Override
    public List<DBChatMessage> findMessagesByUserName(String username) {
        return chatHistoryRepository.find(username);
    }

    @Override
    public DBChatMessage saveChatMessage(DBChatMessage dBChatMessage) {
        return chatHistoryRepository.save(dBChatMessage);
    }

}
