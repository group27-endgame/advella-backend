package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.services.ServiceCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @ApiOperation(value = "Get service categories", notes = "Gets all service categories")
    @GetMapping("/service-categories/all")
    public ResponseEntity<List<ServiceCategory>> getServiceCategories() {
        return ResponseEntity.ok(serviceCategoryService.getServiceCategories());
    }

    @ApiOperation(value = "Delete service category", notes = "Deletes service category by its serviceId")
    @DeleteMapping("/service-categories/dash-board/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable int id) {
        return serviceCategoryService.deleteServiceCategory(id);
    }

    @ApiOperation(value = "Add new service category", notes = "Adds new service category")
    @PostMapping("/service-categories/dash-board")
    public ResponseEntity<Void> addNewServiceCategory(@RequestBody ServiceCategory serviceCategoryToAdd) {
        serviceCategoryService.addNewServiceCategory(serviceCategoryToAdd);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update service category", notes = "Updates service category")
    @PutMapping("/service-categories/dash-board")
    public ResponseEntity<Void> updateServiceCategory(@RequestBody ServiceCategory serviceCategoryToAdd) {
        return serviceCategoryService.updateServiceCategory(serviceCategoryToAdd);
    }
}
