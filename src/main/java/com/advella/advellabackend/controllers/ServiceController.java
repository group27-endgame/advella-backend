package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Picture;
import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Service;
import com.advella.advellabackend.services.ServiceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/services/new")
    public ResponseEntity<Service> addNewService(@RequestBody Service newService) {
        return ResponseEntity.ok(serviceService.addNewService(newService));
    }

    @ApiOperation(value = "Set service picture", notes = "Adds or updated service picture")
    @PutMapping(value = "/services/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> setServicePicture(@RequestParam int serviceId, @RequestBody MultipartFile[] picture) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get service picture", notes = "Gets picture by serviceId")
    @GetMapping(value = "/services/picture")
    public ResponseEntity<Picture> getServicePicture(@RequestParam int serviceId) {
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> openServiceStatus(@RequestParam int serviceId) {
        return serviceService.openService(serviceId);
    }

    @ApiOperation(value = "Close service status", notes = "Sets status of this service as closed")
    @PostMapping("/services/closed/{serviceId}")
    public ResponseEntity<Void> closeServiceStatus(@RequestParam int serviceId) {
        return serviceService.closeService(serviceId);
    }

    @ApiOperation(value = "Delete service", notes = "Deletes service by serviceId")
    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer serviceId) {
        return serviceService.deleteServiceById(serviceId);
    }

    @ApiOperation(value = "Get services between date", notes = "Gets all the services between startDate and endDate")
    @GetMapping("/services/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getServicesBetweenDate(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(serviceService.getServicesCount(new Date(startDate), new Date(endDate)));
    }
}
