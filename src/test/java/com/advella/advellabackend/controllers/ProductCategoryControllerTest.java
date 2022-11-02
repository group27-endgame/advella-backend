package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.repositories.IProductCategoryRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IProductCategoryRepository productCategoryRepository;

    ProductCategory CATEGORY_1 = new ProductCategory(1, "Books", null);
    ProductCategory CATEGORY_2 = new ProductCategory(1, "Games", null);
    ProductCategory CATEGORY_3 = new ProductCategory(1, "Movies", null);

    @Test
    void getAllProductCategories_Multiple() throws Exception {
        List<ProductCategory> contacts = new ArrayList<>(Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3));

        Mockito.when(productCategoryRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/product-categories/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Books")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Games")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Movies")));
    }

    @Test
    void getAllProductCategories_Empty() throws Exception {
        List<ProductCategory> contacts = new ArrayList<>();

        Mockito.when(productCategoryRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/product-categories/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void addProductCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/product-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductCategory_Success() throws Exception {
        Mockito.when(productCategoryRepository.getReferenceById(CATEGORY_1.getProductCategoryId())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/product-categories/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductCategory_Failure() throws Exception {
        Mockito.when(productCategoryRepository.getReferenceById(CATEGORY_1.getProductCategoryId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/product-categories/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProductCategory_Success() throws Exception {
        Mockito.when(productCategoryRepository.getReferenceById(CATEGORY_1.getProductCategoryId())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/product-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductCategory_Failure() throws Exception {
        Mockito.when(productCategoryRepository.getReferenceById(CATEGORY_1.getProductCategoryId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/product-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}