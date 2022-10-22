package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    @Override
    List<ProductCategory> findAll();
}
