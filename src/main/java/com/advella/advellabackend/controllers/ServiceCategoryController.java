package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.services.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @GetMapping("/service-categories/dash-board/all")
    public ResponseEntity<List<ServiceCategory>> getServiceCategories() {
        return ResponseEntity.ok(serviceCategoryService.getServiceCategories());
    }

    @DeleteMapping("/service-categories/dash-board/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable int id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/service-categories/dash-board")
    public ResponseEntity<Void> addNewServiceCategory(@RequestBody ServiceCategory serviceCategoryToAdd) {
        serviceCategoryService.addNewServiceCategory(serviceCategoryToAdd);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/service-categories/dash-board")
    public ResponseEntity<Void> updateServiceCategory(@RequestBody ServiceCategory serviceCategoryToAdd) {
        serviceCategoryService.updateServiceCategory(serviceCategoryToAdd);
        return ResponseEntity.noContent().build();
    }
}
