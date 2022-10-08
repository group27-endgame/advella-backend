package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ReportedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReportedProductRepository extends JpaRepository<ReportedProduct, Integer> {
    @Override
    List<ReportedProduct> findAll();
}
