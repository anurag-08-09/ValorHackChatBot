package com.chat.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.websocketdemo.model.DBChatMessage;

@Transactional
@Repository("chatHistoryRepository")
public interface ChatHistoryRepository extends JpaRepository<DBChatMessage, Integer> {

    @Query("SELECT c FROM DBChatMessage c WHERE c.username = ?1")
    public List<DBChatMessage> find(@Param("username") String username);

}
