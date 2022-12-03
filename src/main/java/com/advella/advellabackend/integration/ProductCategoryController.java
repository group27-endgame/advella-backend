package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.unit.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @ApiOperation(value = "Get product categories", notes = "Gets all product categories")
    @GetMapping("/product-categories/all")
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok(productCategoryService.getProductCategories());
    }

    @ApiOperation(value = "Add product category", notes = "Adds a product category")
    @PostMapping("/product-categories/dash-board")
    public ResponseEntity<Void> addProductCategory(@RequestBody ProductCategory newProductCategory) {
        productCategoryService.addNewProductCategory(newProductCategory);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get product category", notes = "Gets a product category by category id")
    @GetMapping("/product-categories/{categoryId}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable int categoryId) {
        return productCategoryService.getProductCategoryById(categoryId);
    }

    @ApiOperation(value = "Delete product category", notes = "Deletes product category")
    @DeleteMapping("/product-categories/dash-board/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable int id) {
        return productCategoryService.deleteProductCategory(id);
    }

    @ApiOperation(value = "Update product category", notes = "Updates product category")
    @PutMapping("/product-categories/dash-board")
    public ResponseEntity<Void> updateProductCategory(@RequestBody ProductCategory productCategoryToUpdate) {
        return productCategoryService.updateNewProductCategory(productCategoryToUpdate);
    }
}
