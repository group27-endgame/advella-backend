package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IProductRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IProductRepository productRepository;

    @MockBean
    private IUserRepository userRepository;

    private static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWNrIiwicm9sZXMiOlsidXNlciJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL3VzZXJzL2xvZ2luIiwiZXhwIjoxNjY4MTU2NTU1fQ.kvmiR8oZUDZufTxErQgk1CmosOQVfQiz6ir8SS3q7V0";

    User USER1 = new User(1, null, "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER2 = new User(2, null, "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(1, "admin", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER3 = new User(3, "somerandonEmail@gmail.com", "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);

    Product PRODUCT1 = new Product(1, "First", "Detail", Float.valueOf(100.0f), "England", null, null, null, null, new ArrayList<>(), null, null, USER1, null);
    Product PRODUCT2 = new Product(2, "Second", "Detail", null, null, null, null, null, null, null, null, null, null, null);
    Product PRODUCT3 = new Product(3, "Third", "Detail", null, null, null, null, null, null, null, new ProductCategory(20, null, null), null, new User(10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), null);

    @Test
    void getAllProducts_Multiple() throws Exception {
        List<Product> products = new ArrayList<>(Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3));

        Mockito.when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.is("First")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Second")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Third")));
    }

    @Test
    void getAllProducts_Empty() throws Exception {
        List<Product> products = new ArrayList<>();

        Mockito.when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getProductsInCategory_One() throws Exception {
        Mockito.when(productRepository.getProductsWithCategory(PRODUCT3.getProductCategory().getProductCategoryId())).thenReturn(Collections.singletonList(PRODUCT3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/category/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getProductsInCategory_Empty() throws Exception {
        Mockito.when(productRepository.getProductsWithCategory(PRODUCT3.getProductCategory().getProductCategoryId())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/category/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProductsInPostedByUser_One() throws Exception {
        Mockito.when(productRepository.getProductsPostedByUser(10, 1)).thenReturn(Collections.singletonList(PRODUCT3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/user/10?amount=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("Third")));
    }

    @Test
    void getProductsInPostedByUser_Empty() throws Exception {
        Mockito.when(productRepository.getProductsPostedByUser(10, 1)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/user/10?amount=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProductsByLocation_Success() throws Exception {
        Mockito.when(productRepository.getProductsByLocation("England")).thenReturn(Collections.singletonList(PRODUCT1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/dash-board/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("First")));
    }

    @Test
    void getProductsByLocation_Failure() throws Exception {
        Mockito.when(productRepository.getProductsByLocation("England")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/dash-board/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getLatestProducts_Multiple() throws Exception {
        Mockito.when(productRepository.getLatestProducts(3)).thenReturn(Arrays.asList(PRODUCT1, PRODUCT2, PRODUCT3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("First")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Second")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Third")));
    }

    @Test
    void getLatestProducts_Empty() throws Exception {
        Mockito.when(productRepository.getLatestProducts(3)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductCount() throws Exception {
        Mockito.when(productRepository.getProductCount()).thenReturn(3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/dash-board/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(3)));
    }

    @Test
    void getProductsBetweenDates() throws Exception {
        Date startDate = new Date(1667390291L);
        Date endDate = new Date(1667390391L);
        Mockito.when(productRepository.getProductCount(startDate, endDate)).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/dash-board/1667390291/1667390391")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(1)));
    }

    @Test
    void openProductStatus_Success() throws Exception {
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(PRODUCT1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products/open/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productStatus", Matchers.is("open")));
    }

    @Test
    void openProductStatus_Failure() throws Exception {
        Mockito.when(productRepository.findById(1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products/open/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void closeProductStatus_Success() throws Exception {
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(PRODUCT1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products/closed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productStatus", Matchers.is("closed")));
    }

    @Test
    void closeProductStatus_Failure() throws Exception {
        Mockito.when(productRepository.findById(1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products/closed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductById_Success() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("First")));
    }

    @Test
    void getProductById_Failure() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_Success_Owner() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_Success_Admin() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER2);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_Failure_Not_Admin() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(Optional.of(PRODUCT1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER3);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteProduct_Failure() throws Exception {
        Mockito.when(productRepository.findById(PRODUCT1.getProductId())).thenReturn(null);
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}