package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ChatService;
import com.advella.advellabackend.services.ChatServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatServiceController {
    private final ChatServiceService chatServiceService;

    @GetMapping("/chat-services")
    public ResponseEntity<List<ChatService>> getAllChatServices() {
        return ResponseEntity.ok(chatServiceService.getAllChatService());
    }
}
