package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.ReportedProduct;
import com.advella.advellabackend.unit.ReportedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReportedProductController {
    private final ReportedProductService reportedProductService;

    @GetMapping("/reported-products")
    public ResponseEntity<List<ReportedProduct>> getAllReportedProducts() {
        return ResponseEntity.ok(reportedProductService.getAllReportedProduct());
    }
}
