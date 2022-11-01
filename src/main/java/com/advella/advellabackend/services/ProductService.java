package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final IProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByLocation(String location) {
        return productRepository.getProductsByLocation(location);
    }

    public List<Product> getLatestProducts(int amount) {
        List<Product> products = productRepository.getLatestProducts(amount);
        for (Product product : products) {
            product.setPosted(null);
            if (product.getProductCategory() != null) {
                product.getProductCategory().setProducts(new ArrayList<>());
            }
        }
        return products;
    }

    public List<Product> getSearchedProducts(String searchedQuery) {
        return productRepository.findByTitleContaining(searchedQuery);
    }

    public List<Product> getAllProductsWithCategoryId(Integer categoryId) {
        return productRepository.getProductsWithCategory(categoryId);
    }

    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }

    public Integer getProductCount() {
        return productRepository.getProductCount();
    }

    public Product getProductById(int productID) {
        return productRepository.getReferenceById(productID);
    }

    public Integer getClosedProductTotalValue(Date startDate, Date endDate) {
        Integer returnValue = productRepository.getTotalClosedProductValue(startDate, endDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public Integer getAllProductTotalValue(Date startDate, Date endDate) {
        Integer returnValue = productRepository.getTotalProductValue(startDate, endDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public void addNewProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public Integer getProductCount(Date startDate, Date endDate) {
        return productRepository.getProductCount(startDate, endDate);
    }
}
