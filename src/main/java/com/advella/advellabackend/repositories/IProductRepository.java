package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();
}
