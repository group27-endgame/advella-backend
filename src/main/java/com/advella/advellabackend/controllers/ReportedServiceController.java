package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ReportedService;
import com.advella.advellabackend.services.ReportedServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReportedServiceController {
    private final ReportedServiceService reportedService;

    @GetMapping("/reported-services")
    public ResponseEntity<List<ReportedService>> getAllReportedServices() {
        return ResponseEntity.ok(reportedService.getAllReportedServices());
    }
}
