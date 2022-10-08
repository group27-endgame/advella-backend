package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ChatProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatProductRepository extends JpaRepository<ChatProduct,Integer> {
    @Override
    List<ChatProduct> findAll();
}
