package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/product-categories/all")
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok(productCategoryService.getProductCategories());
    }

    @PostMapping("/product-categories")
    public ResponseEntity<Void> addProductCategory(@RequestBody ProductCategory newProductCategory) {
        productCategoryService.addNewProductCategory(newProductCategory);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product-categories/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable int id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/product-categories")
    public ResponseEntity<Void> updateProductCategory(@RequestBody ProductCategory productCategoryToUpdate) {
        productCategoryService.updateNewProductCategory(productCategoryToUpdate);
        return ResponseEntity.noContent().build();
    }
}
