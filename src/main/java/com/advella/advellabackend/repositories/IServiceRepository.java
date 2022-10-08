package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceRepository extends JpaRepository<Service,Integer> {
    @Override
    List<Service> findAll();
}
