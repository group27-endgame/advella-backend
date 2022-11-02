package com.advella.advellabackend.services;

import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.repositories.IServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCategoryService {
    private final IServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategory> getServiceCategories() {
        return serviceCategoryRepository.findAll();
    }

    public void addNewServiceCategory(ServiceCategory newServiceCategory) {
        serviceCategoryRepository.save(newServiceCategory);
    }

    public ResponseEntity<Void> deleteServiceCategory(int id) {
        if (serviceCategoryRepository.getReferenceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        serviceCategoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<ServiceCategory> getServiceCategoryById(int categoryId) {
        if (serviceCategoryRepository.getReferenceById(categoryId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serviceCategoryRepository.getReferenceById(categoryId));
    }

    public ResponseEntity<Void> updateServiceCategory(ServiceCategory newServiceCategory) {
        if (serviceCategoryRepository.getReferenceById(newServiceCategory.getServiceCategoryId()) == null) {
            return ResponseEntity.notFound().build();
        }
        serviceCategoryRepository.save(newServiceCategory);
        return ResponseEntity.ok().build();
    }
}
