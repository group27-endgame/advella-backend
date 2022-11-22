package com.advella.advellabackend.services;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.repositories.IBidProductRepository;
import com.advella.advellabackend.repositories.IProductImageRepository;
import com.advella.advellabackend.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final IProductRepository productRepository;
    private final IProductImageRepository productImageRepository;
    private final IBidProductRepository bidProductRepository;
    private final UserService userService;

    private static final String OPEN_PRODUCT_STATUS = "open";
    private static final String CLOSED_PRODUCT_STATUS = "closed";
    private static final String ADMIN_ROLE = "admin";

    public List<Product> getProducts() {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            if (product.getPosted() != null) {
                product.getPosted().setPostedProduct(null);
                product.getPosted().setPostedService(null);
            }
        }
        return productRepository.findAll();
    }

    public ResponseEntity<List<Product>> getProductsByLocation(String location) {
        List<Product> products = productRepository.getProductsByLocation(location);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (Product product : products) {
            if (product.getPosted() != null) {
                product.getPosted().setPostedProduct(null);
                product.getPosted().setPostedService(null);
            }
        }
        return ResponseEntity.ok(products);
    }

    public List<Product> getLatestProducts(int amount) {
        List<Product> products = productRepository.getLatestProducts(amount);
        for (Product product : products) {
            if (product.getPosted() != null) {
                product.getPosted().setPostedProduct(null);
                product.getPosted().setPostedService(null);
            }
        }
        return products;
    }

    public ResponseEntity<Product> openProduct(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        Product selectedProduct = productRepository.findById(productId).orElseThrow();
        selectedProduct.setProductStatus(OPEN_PRODUCT_STATUS);
        productRepository.save(selectedProduct);

        if (selectedProduct.getPosted() != null) {
            selectedProduct.getPosted().setPostedProduct(null);
            selectedProduct.getPosted().setPostedService(null);
        }

        return ResponseEntity.ok(selectedProduct);
    }

    public ResponseEntity<Product> closeProduct(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        Product selectedProduct = productRepository.findById(productId).orElseThrow();
        selectedProduct.setProductStatus(CLOSED_PRODUCT_STATUS);
        productRepository.save(selectedProduct);

        if (selectedProduct.getPosted() != null) {
            selectedProduct.getPosted().setPostedProduct(null);
            selectedProduct.getPosted().setPostedService(null);
        }

        return ResponseEntity.ok(selectedProduct);
    }

    public List<Product> getSearchedProducts(String searchedQuery) {
        return productRepository.findByTitleContaining(searchedQuery);
    }

    public ResponseEntity<List<Product>> getProductsInPostedByUser(Integer userId, int amount) {
        List<Product> userProducts = productRepository.getProductsPostedByUser(userId, amount);
        if (userProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (Product product : userProducts) {
            if (product.getPosted() != null) {
                product.getPosted().setPostedProduct(null);
                product.getPosted().setPostedService(null);
            }
        }
        return ResponseEntity.ok(userProducts);
    }

    public ResponseEntity<List<Product>> getAllProductsWithCategoryId(Integer categoryId) {
        List<Product> products = productRepository.getProductsWithCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Product product : products) {
            if (product.getPosted() != null) {
                product.getPosted().setPostedProduct(null);
                product.getPosted().setPostedService(null);
            }
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
        productToDelete.getBidProducts().forEach(u -> bidProductRepository.delete(u));
        productToDelete.setBidProducts(null);
        productToDelete.setPosted(null);
        productRepository.delete(productToDelete);
        return ResponseEntity.ok().build();
    }

    public Integer getProductCount() {
        return productRepository.getProductCount();
    }


    public ResponseEntity<Collection<User>> getProductBidders(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(productId).orElseThrow();
        LinkedHashSet<User> users = new LinkedHashSet<>();
        product.getBidProducts().forEach(u -> users.add(u.getProductBidder()));

        for (User user: users) {
            for (Product producto : user.getPostedProduct()) {
                producto.setPosted(null);
            }
            for (com.advella.advellabackend.model.Service service : user.getPostedService()) {
                service.setPosted(null);
            }
        }
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getHighestBidder(int productId) {
        if (!doesProductExist(productId)) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(productId).orElseThrow();
        List<BidProduct> bidsOnProducts = product.getBidProducts();
        bidsOnProducts.sort(Comparator.comparing(BidProduct::getAmount));

        User userToReturn = bidsOnProducts.get(bidsOnProducts.size() - 1).getProductBidder();
        if (userToReturn != null) {
            for (Product producto : userToReturn.getPostedProduct()) {
                producto.setPosted(null);
            }
            for (com.advella.advellabackend.model.Service servic : userToReturn.getPostedService()) {
                servic.setPosted(null);
            }
        }
        return ResponseEntity.ok(userToReturn);
    }

    public ResponseEntity<Product> getProductByIdResponse(int productID) {
        if (!doesProductExist(productID)) {
            return ResponseEntity.notFound().build();
        }
        Product product = productRepository.findById(productID).orElseThrow();

        if (product.getPosted() != null) {
            product.getPosted().setPostedProduct(null);
            product.getPosted().setPostedService(null);
        }
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

    public ResponseEntity<Product> addNewProduct(Product newProduct, String token, MultipartFile multipartFile) {
        User userToAdd = userService.getUserFromHeader(token);
        if (userToAdd == null) {
            ResponseEntity.notFound().build();
        }
        newProduct.setPosted(userToAdd);
        Product productToReturn = productRepository.save(newProduct);

        if (multipartFile != null) {
            try {
                ProductImage newProductImage = productImageRepository.save(new ProductImage(0, productToReturn, saveFile(multipartFile)));
                productToReturn.setProductImages(Collections.singletonList(newProductImage));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        if (productToReturn.getPosted() != null) {
            productToReturn.getPosted().setPostedProduct(null);
            productToReturn.getPosted().setPostedService(null);
        }

        return ResponseEntity.ok(productToReturn);
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

    private String saveFile(MultipartFile multipartFile) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        long fileName = System.currentTimeMillis();

        Path filePath = Paths.get("/app/static/images/" + fileName + "." + extension);
        multipartFile.transferTo(filePath);

        return "/images/" + fileName + "." + extension;
    }
}
