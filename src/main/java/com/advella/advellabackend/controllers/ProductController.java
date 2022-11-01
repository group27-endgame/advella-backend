package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(productService.getAllProductsWithCategoryId(categoryId));
    }

    @GetMapping("/products/user/{userId}")
    public ResponseEntity<List<Product>> getProductsInPostedByUser(@PathVariable("userId") Integer userId, @RequestParam int amount) {
        return ResponseEntity.ok(productService.getProductsInPostedByUser(userId, amount));
    }

    @PostMapping("/products/new")
    public ResponseEntity<Void> addNewProduct(@RequestBody Product newProduct) {
        productService.addNewProduct(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/dash-board/{location}")
    public ResponseEntity<List<Product>> getProductsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(productService.getProductsByLocation(location));
    }

    @GetMapping("/products/latest")
    public ResponseEntity<List<Product>> getFiveLatestProducts(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(productService.getLatestProducts(amount));
    }

    @GetMapping("/products/dash-board/count")
    public ResponseEntity<Integer> getProductCount() {
        return ResponseEntity.ok(productService.getProductCount());
    }

    @DeleteMapping("/products/dash-board/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getProductsBetweenDates(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(productService.getProductCount(new Date(startDate), new Date(endDate)));
    }
}
