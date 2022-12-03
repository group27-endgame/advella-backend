package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.ReportedService;
import com.advella.advellabackend.repositories.IReportedServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportedServiceService {
    private final IReportedServiceRepository reportedServiceRepository;

    public List<ReportedService> getAllReportedServices() {
        return reportedServiceRepository.findAll();
    }
}
