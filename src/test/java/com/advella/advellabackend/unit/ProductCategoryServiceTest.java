package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.repositories.IProductCategoryRepository;
import com.advella.advellabackend.services.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryServiceTest {
    private IProductCategoryRepository productCategoryRepository;

    private ProductCategoryService productCategoryService;

    ProductCategory CATEGORY_1 = new ProductCategory(1, "Books", null);

    @BeforeEach
    void setUp() {
        productCategoryRepository = Mockito.mock(IProductCategoryRepository.class);
        productCategoryService = new ProductCategoryService(productCategoryRepository);
    }

    @Test
    void getProductCategoryById_Success() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<ProductCategory> response = productCategoryService.getProductCategoryById(1);

        assertEquals(ResponseEntity.ok(CATEGORY_1), response);
        assertEquals(CATEGORY_1, response.getBody());
    }

    @Test
    void getProductCategoryById_Failure() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<ProductCategory> response = productCategoryService.getProductCategoryById(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void updateProductCategoryById_Success() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<Void> response = productCategoryService.updateNewProductCategory(CATEGORY_1);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void updateProductCategoryById_Failure() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = productCategoryService.updateNewProductCategory(CATEGORY_1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void deleteProductCategoryById_Success() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(CATEGORY_1);

        ResponseEntity<Void> response = productCategoryService.deleteProductCategory(1);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteProductCategoryById_Failure() {
        Mockito.when(productCategoryRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = productCategoryService.deleteProductCategory(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}