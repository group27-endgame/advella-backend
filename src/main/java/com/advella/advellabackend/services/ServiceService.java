package com.advella.advellabackend.services;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IServiceRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final IServiceRepository serviceRepository;
    private final UserService userService;

    public List<com.advella.advellabackend.model.Service> getServices() {
        return serviceRepository.findAll();
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
        for (com.advella.advellabackend.model.Service service : services) {
            service.setPosted(null);
            if (service.getServiceCategory() != null) {
                service.getServiceCategory().setServices(null);
            }
        }
        return services;
    }

    public ResponseEntity<Void> deleteServiceById(Integer serviceId) {
        if (!doesServiceExist(serviceId)) {
            return ResponseEntity.notFound().build();
        }
        com.advella.advellabackend.model.Service serviceToDelete = serviceRepository.findById(serviceId).orElseThrow();
        serviceToDelete.getUsers().forEach(u -> u.getServices().remove(serviceToDelete));
        userService.saveAllUsers(serviceToDelete.getUsers());
        serviceRepository.delete(serviceToDelete);
        return ResponseEntity.ok().build();
    }

    public Integer getServicesCount() {
        return serviceRepository.getServiceCount();
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

    public List<com.advella.advellabackend.model.Service> getSearchedService(String searchQuery) {
        return serviceRepository.findByTitleContaining(searchQuery);
    }

    public com.advella.advellabackend.model.Service addNewService(com.advella.advellabackend.model.Service newService) {
        return serviceRepository.save(newService);
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
}
