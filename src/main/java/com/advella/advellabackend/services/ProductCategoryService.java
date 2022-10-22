package com.advella.advellabackend.services;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.repositories.IProductCategoryRepository;
import lombok.RequiredArgsConstructor;
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

    public void deleteProductCategory(int id) {
        productCategoryRepository.deleteById(id);
    }

    public void addNewProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void updateNewProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }
}
