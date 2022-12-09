package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Service;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.ServiceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceController {
    private final ServiceService serviceService;

    @ApiOperation(value = "Get services", notes = "Get all services")
    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices() {
        return ResponseEntity.ok(serviceService.getServices());
    }

    @ApiOperation(value = "Add new service", notes = "Adds new service")
    @PostMapping(path = "/services/new")
    public ResponseEntity<Service> addNewService(@RequestPart Service newService, @RequestHeader("Authorization") String token, @RequestPart(name = "image", required = false) MultipartFile image) {
        return serviceService.addNewService(newService, token, image);
    }

    @ApiOperation(value = "Get service by Id", notes = "Gets a service by its serviceId")
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer serviceId) {
        return serviceService.getServiceByIDResponse(serviceId);
    }

    @ApiOperation(value = "Get services posted by user", notes = "Gets requested amount of services posted by user")
    @GetMapping("/services/user/{userId}")
    public ResponseEntity<List<Service>> getServicesInPostedByUser(@PathVariable("userId") Integer userId, @RequestParam int amount) {
        return serviceService.getServicesPostedByUser(userId, amount);
    }

    @ApiOperation(value = "Get services in category", notes = "Gets services by categoryId")
    @GetMapping("/services/category/{categoryId}")
    public ResponseEntity<List<Service>> getServicesInCategory(@PathVariable("categoryId") Integer categoryId) {
        return serviceService.getAllServicesWithCategoryId(categoryId);
    }

    @ApiOperation(value = "Get latest services", notes = "Gets requested amount of latest services")
    @GetMapping("/services/latest")
    public ResponseEntity<List<Service>> getLatestServices(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(serviceService.getLatestServices(amount));
    }

    @ApiOperation(value = "Get services by location", notes = "Gets services by location")
    @GetMapping("/services/dash-board/location/{location}")
    public ResponseEntity<List<Service>> getServicesByLocation(@PathVariable String location) {
        return serviceService.getServicesByLocation(location);
    }

    @ApiOperation(value = "Get service count", notes = "Gets number of all services")
    @GetMapping("/services/dash-board/count")
    public ResponseEntity<Integer> getServiceCount() {
        return ResponseEntity.ok(serviceService.getServicesCount());
    }

    @ApiOperation(value = "Open service status", notes = "Sets status of this service as open")
    @PostMapping("/services/open/{serviceId}")
    public ResponseEntity<Void> openServiceStatus(@PathVariable int serviceId) {
        return serviceService.openService(serviceId);
    }

    @ApiOperation(value = "Close service status", notes = "Sets status of this service as closed")
    @PostMapping("/services/closed/{serviceId}")
    public ResponseEntity<Void> closeServiceStatus(@PathVariable int serviceId) {
        return serviceService.closeService(serviceId);
    }

    @ApiOperation(value = "Delete service", notes = "Deletes service by serviceId")
    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId, @RequestHeader("Authorization") String token) {
        return serviceService.deleteServiceById(serviceId, token);
    }

    @ApiOperation(value = "Get services between date", notes = "Gets all the services between startDate and endDate")
    @GetMapping("/services/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getServicesBetweenDate(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(serviceService.getServicesCount(new Date(startDate), new Date(endDate)));
    }

    @ApiOperation(value = "Get all bidders of service", notes = "Gets all bidders of services")
    @GetMapping("/services/bidders/{serviceId}")
    public ResponseEntity<Collection<User>> getServiceBidders(@PathVariable int serviceId) {
        return serviceService.getServiceBidders(serviceId);
    }

    @ApiOperation(value = "Get highest bidder of service", notes = "Gets highest bidder of service")
    @GetMapping("/services/bidders/highest/{serviceId}")
    public ResponseEntity<User> getHighestBidder(@PathVariable int serviceId) {
        return serviceService.getHighestBidder(serviceId);
    }
}
