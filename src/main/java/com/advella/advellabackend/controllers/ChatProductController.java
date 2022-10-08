package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ChatProduct;
import com.advella.advellabackend.services.ChatProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GroovyWebApplicationContext;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatProductController {
    private final ChatProductService chatProductService;

    @GetMapping("/chat-products")
    public ResponseEntity<List<ChatProduct>> getAllChatProducts() {
        return ResponseEntity.ok(chatProductService.getAllChatProducts());
    }
}
