package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<Product> getFiveLatestProducts(int amount) {
        return productRepository.getFiveLatestProducts(amount);
    }

    public int getProductCount() {
        return productRepository.getProductCount();
    }
}
