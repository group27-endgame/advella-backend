package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();

    @Query(value = "SELECT TOP (:amount) * FROM Products ORDER BY product_posted_datetime DESC", nativeQuery = true)
    List<Product> getLatestProducts(@Param("amount") int amount);

    @Query(value = "SELECT * FROM Products WHERE product_pick_up_location = :location", nativeQuery = true)
    List<Product> getProductsByLocation(String location);

    @Query(value = "SELECT COUNT(*) FROM Products", nativeQuery = true)
    Integer getProductCount();

    @Query(value = "SELECT COUNT(*) FROM Products WHERE product_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getProductCount(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(product_money_amount) FROM Products WHERE product_status = 'closed' AND product_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getTotalClosedProductValue(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(product_money_amount) FROM Products WHERE product_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getTotalProductValue(Date fromDate, Date toDate);

    List<Product> findByTitleContaining(String title);

    @Query(value = "SELECT * FROM Products WHERE category_id = :categoryId", nativeQuery = true)
    List<Product> getProductsWithCategory(Integer categoryId);
}
