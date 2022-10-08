package com.advella.advellabackend.services;

import com.advella.advellabackend.model.ChatProduct;
import com.advella.advellabackend.repositories.IChatProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatProductService {
    private final IChatProductRepository chatProductRepository;

    public List<ChatProduct> getAllChatProducts() {
        return chatProductRepository.findAll();
    }
}
