package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductAndServiceService;
import com.advella.advellabackend.services.ProductService;
import com.advella.advellabackend.services.ServiceService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Get closed products and services", notes = "Gets number of closed products and services")
    @GetMapping("/productsAndServices/dash-board/closed/{startDate}/{endDate}")
    public ResponseEntity<Integer> getClosedProductsAndServices(@PathVariable long startDate, @PathVariable long endDate) {
        Integer totalValue = productService.getClosedProductTotalValue(new Date(startDate), new Date(endDate)) + serviceService.getClosedServiceTotalValue(new Date(startDate), new Date(endDate));
        return ResponseEntity.ok(totalValue);
    }

    @ApiOperation(value = "Get all products and services value", notes = "Gets money value of all products and services from startDate to endDate")
    @GetMapping("/productsAndServices/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getAllProductsAndServicesValue(@PathVariable long startDate, @PathVariable long endDate) {
        Integer totalValue = productService.getAllProductTotalValue(new Date(startDate), new Date(endDate)) + serviceService.getAllServiceTotalValue(new Date(startDate), new Date(endDate));
        return ResponseEntity.ok(totalValue);
    }

    @ApiOperation(value = "Get latest products and services", notes = "Gets requested number of latest products and services")
    @GetMapping("/productsAndServices/latest")
    public ResponseEntity<Collection<Object>> getLatestProductsAndServices(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(productAndServiceService.getLatestProductsAndServices(amount));
    }

    @ApiOperation(value = "Get searched products and services", notes = "Gets all products and services that contain searchQuery in title")
    @GetMapping("/productsAndServices/search")
    public ResponseEntity<Collection<Object>> getSearchedProductsAndServices(@RequestParam String searchedQuery) {
        return ResponseEntity.ok(productAndServiceService.getProductsAndServicesWithSearchedQuery(searchedQuery));
    }
}
