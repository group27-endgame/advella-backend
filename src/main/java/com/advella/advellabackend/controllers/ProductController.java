package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/products/latest")
    public ResponseEntity<List<Product>> getFiveLatestProducts(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(productService.getFiveLatestProducts(amount));
    }

    @GetMapping("/products/{location}")
    public ResponseEntity<List<Product>> getProductsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(productService.getProductsByLocation(location));
    }

    @GetMapping("/products/count")
    public ResponseEntity<Integer> getProductCount() {
        return ResponseEntity.ok(productService.getProductCount());
    }

    @GetMapping("/products/open/{startDate}/{endDate}")
    public ResponseEntity<Integer> getOpenProducts(@PathVariable long startDate, long endDate) {
        return ResponseEntity.ok(productService.getProductCount(new Date(startDate), new Date(endDate)));
    }
}
