package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
    public ResponseEntity<List<Product>> getFiveLatestProducts() {
        return ResponseEntity.ok(productService.getFiveLatestProducts());
    }

    @GetMapping("/products/{location}")
    public ResponseEntity<List<Product>> getProductsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(productService.getProductsByLocation(location));
    }
}
