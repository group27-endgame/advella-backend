package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceCategoryRepository extends JpaRepository<ServiceCategory,Integer> {
    @Override
    List<ServiceCategory> findAll();
}
