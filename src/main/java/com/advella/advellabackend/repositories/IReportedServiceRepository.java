package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ReportedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReportedServiceRepository extends JpaRepository<ReportedService, Integer> {
    @Override
    List<ReportedService> findAll();
}
