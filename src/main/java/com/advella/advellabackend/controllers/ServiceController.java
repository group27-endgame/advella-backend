package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Service;
import com.advella.advellabackend.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    @GetMapping("/services/latest")
    public ResponseEntity<List<Service>> getFiveLatestServices(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(serviceService.getFiveLatestServices(amount));
    }

    @GetMapping("/services/{location}")
    public ResponseEntity<List<Service>> getServicesByLocation(@PathVariable String location) {
        return ResponseEntity.ok(serviceService.getServicesByLocation(location));
    }

    @GetMapping("/services/count")
    public ResponseEntity<Integer> getServiceCount() {
        return ResponseEntity.ok(serviceService.getServicesCount());
    }
}
