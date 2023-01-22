package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.repositories.IServiceRepository;
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
class ServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IServiceRepository serviceRepository;

    @MockBean
    private IUserRepository userRepository;

    private static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWNrIiwicm9sZXMiOlsidXNlciJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL3VzZXJzL2xvZ2luIiwiZXhwIjoxNjY4MTU2NTU1fQ.kvmiR8oZUDZufTxErQgk1CmosOQVfQiz6ir8SS3q7V0";

    User USER1 = new User(1, null, "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER2 = new User(2, null, "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(1, "admin", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER3 = new User(3, "somerandonEmail@gmail.com", "password", "Nick", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);

    Service SERVICE1 = new Service(1, "First", "Detail", Float.valueOf(100.0f), null, null, null, null, null, null, Collections.EMPTY_LIST, null, Collections.EMPTY_LIST, USER1, Collections.EMPTY_LIST);
    Service SERVICE2 = new Service(2, "Second", "Detail", null, null, null, null, null, null, null, Collections.EMPTY_LIST, null, Collections.EMPTY_LIST, null, Collections.EMPTY_LIST);
    Service SERVICE3 = new Service(3, "Third", "Detail", null, null, null, null, null, null, null, Collections.EMPTY_LIST, new ServiceCategory(20, null, null), Collections.EMPTY_LIST, new User(10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), Collections.EMPTY_LIST);

    @Test
    void getAllServices_Multiple() throws Exception {
        List<Service> services = new ArrayList<>(Arrays.asList(SERVICE1, SERVICE2, SERVICE3));

        Mockito.when(serviceRepository.findAll()).thenReturn(services);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.is("First")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Second")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Third")));
    }

    @Test
    void getAllServices_Empty() throws Exception {
        List<Service> services = new ArrayList<>();

        Mockito.when(serviceRepository.findAll()).thenReturn(services);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getServicesInCategory_One() throws Exception {
        Mockito.when(serviceRepository.getServicesWithCategory(SERVICE3.getServiceCategory().getServiceCategoryId())).thenReturn(Collections.singletonList(SERVICE3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/category/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getServicesInCategory_Empty() throws Exception {
        Mockito.when(serviceRepository.getServicesWithCategory(SERVICE3.getServiceCategory().getServiceCategoryId())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/category/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getServicesInPostedByUser_One() throws Exception {
        Mockito.when(serviceRepository.getServicesPostedByUser(10, 1)).thenReturn(Collections.singletonList(SERVICE3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/user/10?amount=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("Third")));
    }

    @Test
    void getServicesInPostedByUser_Empty() throws Exception {
        Mockito.when(serviceRepository.getServicesPostedByUser(10, 1)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/user/10?amount=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getServicesByLocation_Success() throws Exception {
        Mockito.when(serviceRepository.getServicesByLocation("England")).thenReturn(Collections.singletonList(SERVICE1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/dash-board/location/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("First")));
    }

    @Test
    void getServicesByLocation_Failure() throws Exception {
        Mockito.when(serviceRepository.getServicesByLocation("England")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/dash-board/location/England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getLatestServices_Multiple() throws Exception {
        Mockito.when(serviceRepository.getLatestServices(3)).thenReturn(Arrays.asList(SERVICE1, SERVICE2, SERVICE3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is("First")))
                .andExpect(jsonPath("$[1].title", Matchers.is("Second")))
                .andExpect(jsonPath("$[2].title", Matchers.is("Third")));
    }

    @Test
    void getLatestServices_Empty() throws Exception {
        Mockito.when(serviceRepository.getLatestServices(3)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/latest?amount=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getServiceCount() throws Exception {
        Mockito.when(serviceRepository.getServiceCount()).thenReturn(3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/dash-board/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(3)));
    }

    @Test
    void getServicesBetweenDates() throws Exception {
        Date startDate = new Date(1667390291L);
        Date endDate = new Date(1667390391L);
        Mockito.when(serviceRepository.getServiceCount(startDate, endDate)).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/dash-board/1667390291/1667390391")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(1)));
    }

    @Test
    void getServiceById_Success() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("First")));
    }

    @Test
    void getServiceById_Failure() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/services/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteService_Success_Owner() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/services/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteService_Success_Admin() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER2);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/services/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteService_Failure_Not_Admin() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER3);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/services/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteService_Failure() throws Exception {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(null);
        Mockito.when(userRepository.findByUsername("Nick")).thenReturn(USER1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/services/1")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}