package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();

    @Query(value = "SELECT * FROM Products ORDER BY product_id DESC LIMIT 5", nativeQuery = true)
    List<Product> getFiveLatestProducts();

    @Query(value = "SELECT * FROM Products WHERE product_pick_up_location = :location", nativeQuery = true)
    List<Product> getProductsByLocation(String location);
}
