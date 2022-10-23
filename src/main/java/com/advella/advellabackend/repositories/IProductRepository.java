package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();

    @Query(value = "SELECT TOP (:amount) * FROM Products ORDER BY product_id DESC", nativeQuery = true)
    List<Product> getFiveLatestProducts(@Param("amount") int amount);

    @Query(value = "SELECT * FROM Products WHERE product_pick_up_location = :location", nativeQuery = true)
    List<Product> getProductsByLocation(String location);

    @Query(value = "SELECT COUNT(*) FROM Products", nativeQuery = true)
    int getProductCount();
}
