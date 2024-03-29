package com.advella.advellabackend.services;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.repositories.IBidServiceRepository;
import com.advella.advellabackend.repositories.IServiceImageRepository;
import com.advella.advellabackend.repositories.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final IServiceRepository serviceRepository;
    private final UserService userService;
    private final IServiceImageRepository serviceImageRepository;
    private final IBidServiceRepository bidServiceRepository;

    private static final String OPEN_SERVICE_STATUS = "open";
    private static final String CLOSED_SERVICE_STATUS = "closed";
    private static final String ADMIN_ROLE = "admin";

    public List<com.advella.advellabackend.model.Service> getServices() {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.findAll();
        return services;
    }

    public ResponseEntity<List<com.advella.advellabackend.model.Service>> getAllServicesWithCategoryId(Integer categoryId) {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.getServicesWithCategory(categoryId);
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }

    public ResponseEntity<List<com.advella.advellabackend.model.Service>> getServicesPostedByUser(Integer userId, int amount) {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.getServicesPostedByUser(userId, amount);
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }

    public ResponseEntity<List<com.advella.advellabackend.model.Service>> getServicesByLocation(String location) {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.getServicesByLocation(location);
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }

    public List<com.advella.advellabackend.model.Service> getLatestServices(int amount) {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.getLatestServices(amount);
        return services;
    }

    public ResponseEntity<Void> deleteServiceById(Integer serviceId, String token) {
        User user = userService.getUserFromHeader(token);
        if (user == null || !doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }
        com.advella.advellabackend.model.Service serviceToDelete = serviceRepository.findById(serviceId).orElseThrow();

        List<Role> roles = user.getRoles();
        if (serviceToDelete.getPosted().getUserId() != user.getUserId() && roles != null && !isUserAdmin(roles)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        serviceToDelete.getBidServices().forEach(bidService -> bidServiceRepository.deleteById(bidService.getId()));
        serviceToDelete.setBidServices(null);
        if (serviceToDelete.getServiceImages() != null)
            serviceToDelete.getServiceImages().forEach(i -> i.setServiceId(null));
        serviceToDelete.setPosted(null);
        if (serviceToDelete.getBidServices() != null)
            serviceToDelete.getBidServices().forEach(b -> bidServiceRepository.delete(b));
        serviceRepository.delete(serviceToDelete);
        return ResponseEntity.ok().build();
    }

    public Integer getServicesCount() {
        return serviceRepository.getServiceCount();
    }

    public ResponseEntity<Collection<User>> getServiceBidders(int serviceId) {
        if (!doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }

        com.advella.advellabackend.model.Service service = serviceRepository.findById(serviceId).orElseThrow();
        LinkedHashSet<User> users = new LinkedHashSet<>();
        service.getBidServices().forEach(u -> users.add(u.getServiceBidder()));

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getHighestBidder(int serviceId) {
        if (!doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }

        com.advella.advellabackend.model.Service service = serviceRepository.findById(serviceId).orElseThrow();
        List<BidService> bidsOnServices = service.getBidServices();
        bidsOnServices.sort(Comparator.comparing(BidService::getAmount));

        User userToReturn = bidsOnServices.get(bidsOnServices.size() - 1).getServiceBidder();
        return ResponseEntity.ok(userToReturn);
    }

    public ResponseEntity<com.advella.advellabackend.model.Service> getServiceByIDResponse(int serviceId) {
        if (!doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }

        com.advella.advellabackend.model.Service service = serviceRepository.findById(serviceId).orElseThrow();

        return ResponseEntity.ok(service);
    }

    public Integer getClosedServiceTotalValue(Date startDate, Date endDate) {
        Integer returnValue = serviceRepository.getClosedServiceTotalValue(startDate, endDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public Integer getAllServiceTotalValue(Date startDate, Date endDate) {
        Integer returnValue = serviceRepository.getServiceTotalValue(startDate, endDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public ResponseEntity<Void> openService(int serviceId) {
        if (!doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }
        com.advella.advellabackend.model.Service selectedProduct = serviceRepository.findById(serviceId).orElseThrow();
        selectedProduct.setServiceStatus(OPEN_SERVICE_STATUS);
        serviceRepository.save(selectedProduct);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> closeService(int productId) {
        if (!doesServiceExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        com.advella.advellabackend.model.Service selectedProduct = serviceRepository.findById(productId).orElseThrow();
        selectedProduct.setServiceStatus(CLOSED_SERVICE_STATUS);
        serviceRepository.save(selectedProduct);
        return ResponseEntity.ok().build();
    }

    public List<com.advella.advellabackend.model.Service> getSearchedService(String searchQuery) {
        List<com.advella.advellabackend.model.Service> services = serviceRepository.findByTitleContaining(searchQuery);

        return services;
    }

    public ResponseEntity<com.advella.advellabackend.model.Service> addNewService(com.advella.advellabackend.model.Service newService, String token, MultipartFile multipartFile) {
        User userToAdd = userService.getUserFromHeader(token);
        if (userToAdd == null) {
            return ResponseEntity.notFound().build();
        }
        newService.setPosted(userToAdd);
        newService.setPostedDateTime(new java.sql.Date(System.currentTimeMillis()));
        com.advella.advellabackend.model.Service serviceToReturn = serviceRepository.save(newService);
        if (multipartFile != null) {
            try {
                ServiceImage newServiceImage = serviceImageRepository.save(new ServiceImage(0, serviceToReturn, saveFile(multipartFile)));
                serviceToReturn.setServiceImages(Collections.singletonList(newServiceImage));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok(serviceToReturn);
    }

    public Integer getServicesCount(Date startDate, Date endDate) {
        return serviceRepository.getServiceCount(startDate, endDate);
    }

    public boolean doesServiceExist(int serviceId) {
        try {
            serviceRepository.findById(serviceId).orElseThrow();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isUserAdmin(List<Role> roles) {
        for (Role role : roles) {
            if (role.getName().equals(ADMIN_ROLE)) {
                return true;
            }
        }
        return false;
    }

    private String saveFile(MultipartFile multipartFile) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        long fileName = System.currentTimeMillis();

        Path filePath = Paths.get("/app/static/images/" + fileName + "." + extension);
        multipartFile.transferTo(filePath);

        return "/images/" + fileName + "." + extension;
    }
}
