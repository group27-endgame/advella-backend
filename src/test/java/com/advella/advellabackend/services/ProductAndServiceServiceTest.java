package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductAndServiceServiceTest {
    private ServiceService serviceService;
    private ProductService productService;

    private ProductAndServiceService productAndServiceService;

    Product PRODUCT1 = new Product(1, "First", "Detail", Float.valueOf(100.0f), "England", new Date(1668156952), null, null, null, new ArrayList<>(), null, null, null, null, new ArrayList<>());
    Service SERVICE1 = new Service(1, "First", "Detail", Float.valueOf(100.0f), null, new Date(1668156951), null, null, null, null, new ArrayList<>(), null, null, null, null, new ArrayList<>());
    Service SERVICE2 = new Service(2, "Second", "Detail", null, null, new Date(1668156953), null, null, null, null, null, null, null, null, null, new ArrayList<>());

    @BeforeEach
    void setUp() {
        serviceService = Mockito.mock(ServiceService.class);
        productService = Mockito.mock(ProductService.class);
        productAndServiceService = new ProductAndServiceService(serviceService, productService);
    }

    @Test
    void getLatestProductsAndServicesSortedByDate() {
        Mockito.when(serviceService.getLatestServices(3)).thenReturn(Arrays.asList(SERVICE1, SERVICE2));
        Mockito.when(productService.getLatestProducts(3)).thenReturn(Collections.singletonList(PRODUCT1));


        List<Object> productsAndServicesOrderedByDate = productAndServiceService.getLatestProductsAndServices(3);

        assertEquals(3, productsAndServicesOrderedByDate.size());
        assertEquals(SERVICE1, productsAndServicesOrderedByDate.get(0));
        assertEquals(PRODUCT1, productsAndServicesOrderedByDate.get(1));
        assertEquals(SERVICE2, productsAndServicesOrderedByDate.get(2));
    }
}