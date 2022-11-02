package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.ProductCategory;
import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.repositories.IServiceCategoryRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ServiceCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IServiceCategoryRepository serviceCategoryRepository;

    ServiceCategory CATEGORY_1 = new ServiceCategory(1, "Books", null);
    ServiceCategory CATEGORY_2 = new ServiceCategory(1, "Games", null);
    ServiceCategory CATEGORY_3 = new ServiceCategory(1, "Movies", null);

    @Test
    void getAllServiceCategories_Multiple() throws Exception {
        List<ServiceCategory> contacts = new ArrayList<>(Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3));

        Mockito.when(serviceCategoryRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/service-categories/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Books")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Games")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Movies")));
    }

    @Test
    void getAllServiceCategories_Empty() throws Exception {
        List<ServiceCategory> contacts = new ArrayList<>();

        Mockito.when(serviceCategoryRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/service-categories/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void addServiceCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/service-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteServiceCategory_Success() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/service-categories/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteServiceCategory_Failure() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/service-categories/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getServiceCategory_Success() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/service-categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("Books")));
    }

    @Test
    void getServiceCategory_Failure() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/service-categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateServiceCategory_Success() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/service-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateServiceCategory_Failure() throws Exception {
        Mockito.when(serviceCategoryRepository.getReferenceById(CATEGORY_1.getServiceCategoryId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/service-categories/dash-board")
                        .content(this.objectMapper.writeValueAsString(CATEGORY_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}