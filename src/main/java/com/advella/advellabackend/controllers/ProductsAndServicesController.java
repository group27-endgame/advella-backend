package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductService;
import com.advella.advellabackend.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductsAndServicesController {
    private final ProductService productService;
    private final ServiceService serviceService;

    @GetMapping("/productsAndServices/{startDate}/{endDate}")
    public ResponseEntity<Integer> getProducts(@PathVariable long startDate, long endDate) {
        Integer totalValue = productService.getClosedProductTotalValue(new Date(startDate), new Date(endDate)) + serviceService.getClosedServiceTotalValue(new Date(startDate), new Date(endDate));
        return ResponseEntity.ok(totalValue);
    }
}
