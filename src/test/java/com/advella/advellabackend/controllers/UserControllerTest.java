package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IRoleRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import com.advella.advellabackend.services.ProductService;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private IRoleRepository roleRepository;

    User USER1 = new User(1, null, "password", "Nick", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    User USER2 = new User(2, null, "password1234", "Bob", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    User USER3 = new User(3, null, "password4321", "Dan", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    @Test
    void getUsersByRole_Multiple() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(USER1, USER2, USER3));

        Mockito.when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].username", Matchers.is("Nick")))
                .andExpect(jsonPath("$[1].username", Matchers.is("Bob")))
                .andExpect(jsonPath("$[2].username", Matchers.is("Dan")));
    }

    @Test
    void getUsersByRole_Empty() throws Exception {
        List<User> users = new ArrayList<>(Collections.EMPTY_LIST);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_Success() throws Exception {
        Mockito.when(userRepository.findByUsername(USER1.getUsername())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER1)))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_Failure() throws Exception {
        Mockito.when(userRepository.findByUsername(USER1.getUsername())).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserById_Success() throws Exception {
        Mockito.when(userRepository.getReferenceById(USER1.getUserId())).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById_Failure() throws Exception {
        Mockito.when(userRepository.getReferenceById(USER1.getUserId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLatestUsers_Multiple() throws Exception {
        Mockito.when(userRepository.getLatestUsers(3)).thenReturn(Arrays.asList(USER1, USER2, USER3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", Matchers.is("Nick")))
                .andExpect(jsonPath("$[1].username", Matchers.is("Bob")))
                .andExpect(jsonPath("$[2].username", Matchers.is("Dan")));
    }

    @Test
    void getLatestServices_Empty() throws Exception {
        Mockito.when(userRepository.getLatestUsers(3)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersByLocation_Success() throws Exception {
        Mockito.when(userRepository.getUsersByLocation("England")).thenReturn(Collections.singletonList(USER1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/location/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", Matchers.is("Nick")));
    }

    @Test
    void getUsersByLocation_Failure() throws Exception {
        Mockito.when(userRepository.getUsersByLocation("England")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/dash-board/location/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_Success() throws Exception {
        Mockito.when(userRepository.getReferenceById(USER1.getUserId())).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_Failure() throws Exception {
        Mockito.when(userRepository.getReferenceById(USER1.getUserId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}