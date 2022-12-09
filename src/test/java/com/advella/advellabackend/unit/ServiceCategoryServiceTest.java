package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.repositories.IServiceCategoryRepository;
import com.advella.advellabackend.services.ServiceCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCategoryServiceTest {
    private IServiceCategoryRepository serviceCategoryRepository;

    private ServiceCategoryService serviceCategoryService;

    ServiceCategory CATEGORY_1 = new ServiceCategory(1, "Books", null);

    @BeforeEach
    void setUp() {
        serviceCategoryRepository = Mockito.mock(IServiceCategoryRepository.class);
        serviceCategoryService = new ServiceCategoryService(serviceCategoryRepository);
    }

    @Test
    void getProductCategoryById_Success() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<ServiceCategory> response = serviceCategoryService.getServiceCategoryById(1);

        assertEquals(ResponseEntity.ok(CATEGORY_1), response);
        assertEquals(CATEGORY_1, response.getBody());
    }

    @Test
    void getProductCategoryById_Failure() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<ServiceCategory> response = serviceCategoryService.getServiceCategoryById(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void updateProductCategoryById_Success() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<Void> response = serviceCategoryService.updateServiceCategory(CATEGORY_1);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void updateProductCategoryById_Failure() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = serviceCategoryService.updateServiceCategory(CATEGORY_1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void deleteProductCategoryById_Success() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<Void> response = serviceCategoryService.deleteServiceCategory(1);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteProductCategoryById_Failure() {
        Mockito.when(serviceCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = serviceCategoryService.deleteServiceCategory(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}