package com.advella.advellabackend.services;

import com.advella.advellabackend.model.ChatService;
import com.advella.advellabackend.repositories.IChatServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceService {
    private final IChatServiceRepository chatServiceRepository;

    public List<ChatService> getAllChatService() {
        return chatServiceRepository.findAll();
    }
}
