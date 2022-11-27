package com.advella.advellabackend.services;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.model.chat.ChatMessage;
import com.advella.advellabackend.model.chat.MessageStatus;
import com.advella.advellabackend.repositories.IChatMessageRepository;
import com.advella.advellabackend.repositories.IChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    private final IChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setChatStatus(MessageStatus.RECEIVED);
        return chatMessageRepository.save(chatMessage);
    }

    public long countNewMessages(Integer senderId, Integer recipientId) {
        return chatMessageRepository.countByChatMessageSender_UserIdAndChatMessageRecipient_UserIdAndChatStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(Integer senderId, Integer recipientId) {
        String chatId;
        try {
            chatId = chatRoomService.getChatId(senderId, recipientId, false).orElseThrow();
        } catch (Exception e) {
            return Collections.emptyList();
        }

        List<ChatMessage> chatMessages = chatMessageRepository.findByChatId(chatId);

        if (!chatMessages.isEmpty()) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return chatMessages;
    }

    public ChatMessage findById(Integer id) {
        return chatMessageRepository.findById(id)
                .map(chatMessage -> {
                    chatMessage.setChatStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                }).orElseThrow(() -> new IllegalArgumentException());
    }

    public void updateStatuses(Integer senderId, Integer recipientId, MessageStatus status) {
        chatMessageRepository.updateChatStatusByChatMessageSender_UserIdAndChatMessageRecipient_UserId(status, senderId, recipientId);
    }
}
