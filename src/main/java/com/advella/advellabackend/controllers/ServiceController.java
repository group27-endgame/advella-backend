package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Service;
import com.advella.advellabackend.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices() {
        return ResponseEntity.ok(serviceService.getServices());
    }

    @PostMapping("/services/new")
    public ResponseEntity<Void> addNewService(@RequestBody Service newService) {
        serviceService.addNewService(newService);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/services/latest")
    public ResponseEntity<List<Service>> getFiveLatestServices(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(serviceService.getLatestServices(amount));
    }

    @GetMapping("/services/dash-board/{location}")
    public ResponseEntity<List<Service>> getServicesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(serviceService.getServicesByLocation(location));
    }

    @GetMapping("/services/dash-board/count")
    public ResponseEntity<Integer> getServiceCount() {
        return ResponseEntity.ok(serviceService.getServicesCount());
    }

    @DeleteMapping("/services/dash-board/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
        serviceService.deleteServiceById(serviceId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/services/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getServicesBetweenDate(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(serviceService.getServicesCount(new Date(startDate), new Date(endDate)));
    }
}
