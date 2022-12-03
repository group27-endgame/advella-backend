package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.repositories.IProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCategoryService {
    private final IProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ResponseEntity<Void> deleteProductCategory(int id) {
        if (productCategoryRepository.getReferenceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productCategoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<ProductCategory> getProductCategoryById(int categoryId) {
        if (productCategoryRepository.getReferenceById(categoryId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productCategoryRepository.getReferenceById(categoryId));
    }

    public void addNewProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public ResponseEntity<Void> updateNewProductCategory(ProductCategory productCategory) {
        if (productCategoryRepository.getReferenceById(productCategory.getProductCategoryId()) == null) {
            return ResponseEntity.notFound().build();
        }
        productCategoryRepository.save(productCategory);
        return ResponseEntity.ok().build();
    }
}
