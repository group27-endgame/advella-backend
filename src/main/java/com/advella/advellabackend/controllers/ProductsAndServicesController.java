package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductAndServiceService;
import com.advella.advellabackend.services.ProductService;
import com.advella.advellabackend.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ObjectView;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductsAndServicesController {
    private final ProductService productService;
    private final ServiceService serviceService;
    private final ProductAndServiceService productAndServiceService;

    @GetMapping("/productsAndServices/dash-board/closed/{startDate}/{endDate}")
    public ResponseEntity<Integer> getClosedProductsAndServices(@PathVariable long startDate, @PathVariable long endDate) {
        Integer totalValue = productService.getClosedProductTotalValue(new Date(startDate), new Date(endDate)) + serviceService.getClosedServiceTotalValue(new Date(startDate), new Date(endDate));
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/productsAndServices/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getAllProductsAndServicesValue(@PathVariable long startDate, @PathVariable long endDate) {
        Integer totalValue = productService.getAllProductTotalValue(new Date(startDate), new Date(endDate)) + serviceService.getAllServiceTotalValue(new Date(startDate), new Date(endDate));
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/productsAndServices/latest")
    public ResponseEntity<Collection<Object>> getLatestProductsAndServices(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(productAndServiceService.getLatestProductsAndServices(amount));
    }

    @GetMapping("/productsAndServices/search")
    public ResponseEntity<Collection<Object>> getSearchedProductsAndServices(@RequestParam String searchedQuery) {
        return ResponseEntity.ok(productAndServiceService.getProductsAndServicesWithSearchedQuery(searchedQuery));
    }
}
