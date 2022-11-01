package com.advella.advellabackend.services;

import com.advella.advellabackend.repositories.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final IServiceRepository serviceRepository;

    public List<com.advella.advellabackend.model.Service> getServices() {
        return serviceRepository.findAll();
    }

    public List<com.advella.advellabackend.model.Service> getServicesByLocation(String location) {
        return serviceRepository.getServicesByLocation(location);
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

    public void deleteServiceById(Integer serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public Integer getServicesCount() {
        return serviceRepository.getServiceCount();
    }

    public com.advella.advellabackend.model.Service getServiceByID(int serviceId) {
        return serviceRepository.getReferenceById(serviceId);
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

    public void addNewService(com.advella.advellabackend.model.Service newService) {
        serviceRepository.save(newService);
    }

    public Integer getServicesCount(Date startDate, Date endDate) {
        return serviceRepository.getServiceCount(startDate, endDate);
    }
}
