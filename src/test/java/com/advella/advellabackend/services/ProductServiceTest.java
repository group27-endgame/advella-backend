package com.advella.advellabackend.services;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.repositories.IBidProductRepository;
import com.advella.advellabackend.repositories.IProductImageRepository;
import com.advella.advellabackend.repositories.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private IProductRepository productRepository;
    private IProductImageRepository productImageRepository;
    private IBidProductRepository bidProductRepository;
    private UserService userService;

    private ProductService productService;

    private static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWNrIiwicm9sZXMiOlsidXNlciJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL3VzZXJzL2xvZ2luIiwiZXhwIjoxNjY4MTU2NTU1fQ.kvmiR8oZUDZufTxErQgk1CmosOQVfQiz6ir8SS3q7V0";

    User USER1 = new User(1, null, "password", "Nick1", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER2 = new User(2, null, "password", "Nick2", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(1, "admin", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);

    Product PRODUCT1 = new Product(1, "First", "Detail", Float.valueOf(100.0f), "England", null, null, null, null, new ArrayList<>(), null, null, USER1, null);
    Product PRODUCT2 = new Product(2, "Second", "Detail", null, null, null, null, null, null, new ArrayList<>(), null, null, USER1, null);
    Product PRODUCT3 = new Product(3, "Third", "Detail", null, null, null, null, null, null, new ArrayList<>(), new ProductCategory(20, null, null), null, new User(10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), null);

    @BeforeEach
    void setUp() {
        bidProductRepository = Mockito.mock(IBidProductRepository.class);
        productRepository = Mockito.mock(IProductRepository.class);
        productImageRepository = Mockito.mock(IProductImageRepository.class);
        userService = Mockito.mock(UserService.class);
        productService = new ProductService(productRepository, productImageRepository, bidProductRepository, userService);
    }

    @Test
    void getProductsByLocation_Success() {
        List<Product> products = Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3);
        Mockito.when(productRepository.getProductsByLocation("TEST")).thenReturn(products);

        ResponseEntity<List<Product>> response = productService.getProductsByLocation("TEST");

        assertEquals(ResponseEntity.ok(products), response);
        assertEquals(PRODUCT1, response.getBody().get(0));
        assertEquals(PRODUCT2, response.getBody().get(1));
        assertEquals(PRODUCT3, response.getBody().get(2));
    }

    @Test
    void getProductsByLocation_Failure() {
        Mockito.when(productRepository.getProductsByLocation("TEST")).thenReturn(new ArrayList<>());

        ResponseEntity<List<Product>> response = productService.getProductsByLocation("TEST");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void getLatestProducts_Success() {
        List<Product> products = Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3);
        Mockito.when(productRepository.getLatestProducts(3)).thenReturn(products);

        List<Product> response = productService.getLatestProducts(3);

        assertEquals(response.get(0), PRODUCT1);
        assertEquals(null, PRODUCT1.getPosted());
        assertEquals(null, PRODUCT1.getProductCategory());
        assertEquals(response.get(1), PRODUCT2);
        assertEquals(null, PRODUCT2.getPosted());
        assertEquals(null, PRODUCT2.getProductCategory());
        assertEquals(response.get(2), PRODUCT3);
        assertEquals(null, PRODUCT3.getPosted());
        assertEquals(0, PRODUCT3.getProductCategory().getProducts().size());
    }

    @Test
    void openProduct_Success() {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));

        ResponseEntity<Product> response = productService.openProduct(PRODUCT1.getProductId());

        assertEquals(ResponseEntity.ok(PRODUCT1), response);
        assertEquals("open", PRODUCT1.getProductStatus());
    }

    @Test
    void openProduct_Failure() {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(null);

        ResponseEntity<Product> response = productService.openProduct(PRODUCT1.getProductId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void closeProduct_Success() {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));

        ResponseEntity<Product> response = productService.closeProduct(PRODUCT1.getProductId());

        assertEquals(ResponseEntity.ok(PRODUCT1), response);
        assertEquals("closed", PRODUCT1.getProductStatus());
    }

    @Test
    void closeProduct_Failure() {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(null);

        ResponseEntity<Product> response = productService.closeProduct(PRODUCT1.getProductId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void getProductsInPostedByUser_Success() {
        List<Product> productList = Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3);
        Mockito.when(productRepository.getProductsPostedByUser(USER1.getUserId(), 3)).thenReturn(productList);

        ResponseEntity<List<Product>> response = productService.getProductsInPostedByUser(USER1.getUserId(), 3);

        assertEquals(ResponseEntity.ok(productList), response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    void getProductsInPostedByUser_Failure() {
        Mockito.when(productRepository.getProductsPostedByUser(USER1.getUserId(), 3)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Product>> response = productService.getProductsInPostedByUser(USER1.getUserId(), 3);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void getAllProductsWithCategoryId_Success() {
        List<Product> productList = Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3);
        Mockito.when(productRepository.getProductsWithCategory(5)).thenReturn(productList);

        ResponseEntity<List<Product>> response = productService.getAllProductsWithCategoryId(5);

        assertEquals(ResponseEntity.ok(productList), response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    void getAllProductsWithCategoryId_Failure() {
        Mockito.when(productRepository.getProductsWithCategory(5)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Product>> response = productService.getAllProductsWithCategoryId(5);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void deleteProductById_Success_Posted() {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = productService.deleteProductById(PRODUCT1.getProductId(), TEST_TOKEN);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteProductById_Success_Admin() {
        Mockito.when(productRepository.findById(PRODUCT2.getProductId())).thenReturn(Optional.of(PRODUCT2));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER2);

        ResponseEntity<Void> response = productService.deleteProductById(PRODUCT2.getProductId(), TEST_TOKEN);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteProductById_Failure_Not_Admin() {
        Mockito.when(productRepository.findById(PRODUCT3.getProductId())).thenReturn(Optional.of(PRODUCT3));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = productService.deleteProductById(PRODUCT3.getProductId(), TEST_TOKEN);

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), response);
    }

    @Test
    void deleteProductById_Failure_Not_Found() {
        Mockito.when(productRepository.findById(PRODUCT3.getProductId())).thenReturn(null);
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = productService.deleteProductById(PRODUCT3.getProductId(), TEST_TOKEN);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void getProductByIdResponse_Success() {
        Mockito.when(productRepository.findById(PRODUCT3.getProductId())).thenReturn(Optional.of(PRODUCT3));

        ResponseEntity<Product> response = productService.getProductByIdResponse(PRODUCT3.getProductId());

        assertEquals(ResponseEntity.ok(PRODUCT3), response);
    }

    @Test
    void getProductByIdResponse_Failure() {
        Mockito.when(productRepository.findById(PRODUCT3.getProductId())).thenReturn(null);

        ResponseEntity<Product> response = productService.getProductByIdResponse(PRODUCT3.getProductId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}