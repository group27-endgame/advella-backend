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

    public List<com.advella.advellabackend.model.Service> getFiveLatestServices(int amount) {
        return serviceRepository.getFiveLatestServices(amount);
    }

    public int getServicesCount() {
        return serviceRepository.getServiceCount();
    }

    public int getServicesCount(Date startDate, Date endDate) {
        return serviceRepository.getServiceCount(startDate, endDate);
    }
}
