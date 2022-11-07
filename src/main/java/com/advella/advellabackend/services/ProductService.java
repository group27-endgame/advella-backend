package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IProductRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    private static final String OPEN_PRODUCT_STATUS = "open";
    private static final String CLOSED_PRODUCT_STATUS = "closed";
    private static final String ADMIN_ROLE = "admin";

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<List<Product>> getProductsByLocation(String location) {
        List<Product> products = productRepository.getProductsByLocation(location);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
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

    public ResponseEntity<Void> openProduct(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        Product selectedProduct = productRepository.findById(productId).orElseThrow();
        selectedProduct.setProductStatus(OPEN_PRODUCT_STATUS);
        productRepository.save(selectedProduct);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> closeProduct(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        Product selectedProduct = productRepository.findById(productId).orElseThrow();
        selectedProduct.setProductStatus(CLOSED_PRODUCT_STATUS);
        productRepository.save(selectedProduct);
        return ResponseEntity.ok().build();
    }

    public List<Product> getSearchedProducts(String searchedQuery) {
        return productRepository.findByTitleContaining(searchedQuery);
    }

    public ResponseEntity<List<Product>> getProductsInPostedByUser(Integer userId, int amount) {
        List<Product> userProducts = productRepository.getProductsPostedByUser(userId, amount);
        if (userProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userProducts);
    }

    public ResponseEntity<List<Product>> getAllProductsWithCategoryId(Integer categoryId) {
        List<Product> products = productRepository.getProductsWithCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<Void> deleteProductById(Integer productId, String token) {
        User user = userService.getUserFromHeader(token);
        if (user == null || !doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        Product productToDelete = productRepository.findById(productId).orElseThrow();

        List<Role> roles = user.getRoles();
        if (productToDelete.getPosted().getUserId() != user.getUserId() && roles != null && !isUserAdmin(roles)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        productToDelete.getUsers().forEach(u -> u.getProducts().remove(productToDelete));
        userService.saveAllUsers(productToDelete.getUsers());
        productToDelete.setUsers(null);
        productToDelete.setPosted(null);
        productRepository.delete(productToDelete);
        return ResponseEntity.ok().build();
    }

    public Integer getProductCount() {
        return productRepository.getProductCount();
    }

    public ResponseEntity<Product> getProductByIdResponse(int productID) {
        if (!doesProductExist(productID)) {
            return ResponseEntity.notFound().build();
        }
        Product product = productRepository.findById(productID).orElseThrow();
        return ResponseEntity.ok(product);
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

    public ResponseEntity<Product> addNewProduct(Product newProduct, String token) {
        User userToAdd = userService.getUserFromHeader(token);
        if (userToAdd == null) {
            ResponseEntity.notFound().build();
        }
        newProduct.setPosted(userToAdd);
        return ResponseEntity.ok(productRepository.save(newProduct));
    }

    public Integer getProductCount(Date startDate, Date endDate) {
        return productRepository.getProductCount(startDate, endDate);
    }

    public boolean doesProductExist(int productId) {
        try {
            productRepository.findById(productId).orElseThrow();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isUserAdmin(List<Role> roles) {
        for (Role role : roles) {
            if (role.getName().equals(ADMIN_ROLE)) {
                return true;
            }
        }
        return false;
    }
}
