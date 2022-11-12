package com.advella.advellabackend.services;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.repositories.IServiceImageRepository;
import com.advella.advellabackend.repositories.IServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceServiceTest {

    private IServiceRepository serviceRepository;
    private IServiceImageRepository serviceImageRepository;
    private UserService userService;

    private ServiceService serviceService;

    private static final String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWNrIiwicm9sZXMiOlsidXNlciJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL3VzZXJzL2xvZ2luIiwiZXhwIjoxNjY4MTU2NTU1fQ.kvmiR8oZUDZufTxErQgk1CmosOQVfQiz6ir8SS3q7V0";

    User USER1 = new User(1, null, "password", "Nick1", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(0, "user", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);
    User USER2 = new User(2, null, "password", "Nick2", null, null, null, new ArrayList<Role>(Arrays.asList(new Role(1, "admin", null))), new ArrayList<>(), new ArrayList<>(), null, null, null, null, new ArrayList<>(), new ArrayList<>(), null, null, null, null);

    Service SERVICE1 = new Service(1, "First", "Detail", Float.valueOf(100.0f), null, null, null, null, null, null, new ArrayList<>(), null, null, USER1, null, new ArrayList<>());
    Service SERVICE2 = new Service(2, "Second", "Detail", null, null, null, null, null, null, null, new ArrayList<>(), null, null, USER1, null, new ArrayList<>());
    Service SERVICE3 = new Service(3, "Third", "Detail", null, null, null, null, null, null, null, null, new ServiceCategory(20, null, null), null, new User(10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), null, new ArrayList<>());

    @BeforeEach
    void setUp() {
        serviceRepository = Mockito.mock(IServiceRepository.class);
        serviceImageRepository = Mockito.mock(IServiceImageRepository.class);
        userService = Mockito.mock(UserService.class);
        serviceService = new ServiceService(serviceRepository, userService, serviceImageRepository);
    }

    @Test
    void getServicesByLocation_Success() {
        List<Service> services = Arrays.asList(SERVICE1, SERVICE2, SERVICE3);
        Mockito.when(serviceRepository.getServicesByLocation("TEST")).thenReturn(services);

        ResponseEntity<List<Service>> response = serviceService.getServicesByLocation("TEST");

        assertEquals(ResponseEntity.ok(services), response);
        assertEquals(SERVICE1, response.getBody().get(0));
        assertEquals(SERVICE2, response.getBody().get(1));
        assertEquals(SERVICE3, response.getBody().get(2));
    }

    @Test
    void getServicesByLocation_Failure() {
        Mockito.when(serviceRepository.getServicesByLocation("TEST")).thenReturn(new ArrayList<>());

        ResponseEntity<List<Service>> response = serviceService.getServicesByLocation("TEST");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void getLatestServices_Success() {
        List<Service> services = Arrays.asList(SERVICE1, SERVICE2, SERVICE3);
        Mockito.when(serviceRepository.getLatestServices(3)).thenReturn(services);

        List<Service> response = serviceService.getLatestServices(3);

        assertEquals(response.get(0), SERVICE1);
        assertEquals(null, SERVICE1.getPosted());
        assertEquals(null, SERVICE1.getServiceCategory());
        assertEquals(response.get(1), SERVICE2);
        assertEquals(null, SERVICE2.getPosted());
        assertEquals(null, SERVICE2.getServiceCategory());
        assertEquals(response.get(2), SERVICE3);
        assertEquals(null, SERVICE3.getPosted());
        assertEquals(null, SERVICE3.getServiceCategory().getServices());
    }

    @Test
    void openService_Success() {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));

        ResponseEntity<Void> response = serviceService.openService(SERVICE1.getServiceId());

        assertEquals(ResponseEntity.ok().build(), response);
        assertEquals("open", SERVICE1.getServiceStatus());
    }

    @Test
    void openService_Failure() {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(null);

        ResponseEntity<Void> response = serviceService.openService(SERVICE1.getServiceId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void closeService_Success() {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));

        ResponseEntity<Void> response = serviceService.closeService(SERVICE1.getServiceId());

        assertEquals(ResponseEntity.ok().build(), response);
        assertEquals("closed", SERVICE1.getServiceStatus());
    }

    @Test
    void closeService_Failure() {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(null);

        ResponseEntity<Void> response = serviceService.closeService(SERVICE1.getServiceId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void getServicesInPostedByUser_Success() {
        List<Service> services = Arrays.asList(SERVICE1, SERVICE2, SERVICE3);
        Mockito.when(serviceRepository.getServicesPostedByUser(USER1.getUserId(), 3)).thenReturn(services);

        ResponseEntity<List<Service>> response = serviceService.getServicesPostedByUser(USER1.getUserId(), 3);

        assertEquals(ResponseEntity.ok(services), response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    void getServicesInPostedByUser_Failure() {
        Mockito.when(serviceRepository.getServicesPostedByUser(USER1.getUserId(), 3)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Service>> response = serviceService.getServicesPostedByUser(USER1.getUserId(), 3);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void getAllServicesWithCategoryId_Success() {
        List<Service> services = Arrays.asList(SERVICE1, SERVICE2, SERVICE3);
        Mockito.when(serviceRepository.getServicesWithCategory(5)).thenReturn(services);

        ResponseEntity<List<Service>> response = serviceService.getAllServicesWithCategoryId(5);

        assertEquals(ResponseEntity.ok(services), response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    void getAllServicesWithCategoryId_Failure() {
        Mockito.when(serviceRepository.getServicesWithCategory(5)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Service>> response = serviceService.getAllServicesWithCategoryId(5);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void deleteServiceById_Success_Posted() {
        Mockito.when(serviceRepository.findById(SERVICE1.getServiceId())).thenReturn(Optional.of(SERVICE1));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = serviceService.deleteServiceById(SERVICE1.getServiceId(), TEST_TOKEN);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteServiceById_Success_Admin() {
        Mockito.when(serviceRepository.findById(SERVICE2.getServiceId())).thenReturn(Optional.of(SERVICE2));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER2);

        ResponseEntity<Void> response = serviceService.deleteServiceById(SERVICE2.getServiceId(), TEST_TOKEN);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteServiceById_Failure_Not_Admin() {
        Mockito.when(serviceRepository.findById(SERVICE3.getServiceId())).thenReturn(Optional.of(SERVICE3));
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = serviceService.deleteServiceById(SERVICE3.getServiceId(), TEST_TOKEN);

        assertEquals(ResponseEntity.status(HttpStatus.FORBIDDEN).build(), response);
    }

    @Test
    void deleteServiceById_Failure_Not_Found() {
        Mockito.when(serviceRepository.findById(SERVICE3.getServiceId())).thenReturn(null);
        Mockito.when(userService.getUserFromHeader(TEST_TOKEN)).thenReturn(USER1);

        ResponseEntity<Void> response = serviceService.deleteServiceById(SERVICE3.getServiceId(), TEST_TOKEN);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void getServiceByIdResponse_Success() {
        Mockito.when(serviceRepository.findById(SERVICE3.getServiceId())).thenReturn(Optional.of(SERVICE3));

        ResponseEntity<Service> response = serviceService.getServiceByIDResponse(SERVICE3.getServiceId());

        assertEquals(ResponseEntity.ok(SERVICE3), response);
    }

    @Test
    void getServiceByIdResponse_Failure() {
        Mockito.when(serviceRepository.findById(SERVICE3.getServiceId())).thenReturn(null);

        ResponseEntity<Service> response = serviceService.getServiceByIDResponse(SERVICE3.getServiceId());

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}