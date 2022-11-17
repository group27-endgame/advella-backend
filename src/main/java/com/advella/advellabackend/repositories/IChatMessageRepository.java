package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.chat.ChatMessage;
import com.advella.advellabackend.model.chat.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    long countByChatMessageSender_UserIdAndChatMessageRecipient_UserIdAndChatStatus(int userId, int userId1, MessageStatus chatStatus);

    List<ChatMessage> findByChatId(String chatId);

    @Transactional
    @Modifying
    @Query("""
            update ChatMessage c set c.chatStatus = ?1
            where c.chatMessageSender.userId = ?2 and c.chatMessageRecipient.userId = ?3""")
    void updateChatStatusByChatMessageSender_UserIdAndChatMessageRecipient_UserId(MessageStatus chatStatus, int userId, int userId1);

}
