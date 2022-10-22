package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Category;
import com.advella.advellabackend.services.ProductCategoryService;
import com.advella.advellabackend.services.ServiceCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CategoriesController {
    private final ProductCategoryService productCategoryService;
    private final ServiceCategoryService serviceCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = new ArrayList<>();
        allCategories.addAll(productCategoryService.getProductCategories());
        allCategories.addAll(serviceCategoryService.getServiceCategories());
        return ResponseEntity.ok(allCategories);
    }
}
