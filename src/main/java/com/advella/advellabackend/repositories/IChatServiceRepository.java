package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ChatService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatServiceRepository extends JpaRepository<ChatService, Integer> {
    @Override
    List<ChatService> findAll();
}
