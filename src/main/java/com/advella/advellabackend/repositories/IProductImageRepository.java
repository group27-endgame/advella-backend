package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
