package com.advella.advellabackend.services;

import com.advella.advellabackend.repositories.IServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final IServiceRepository serviceRepository;

    public List<com.advella.advellabackend.model.Service> getServices() {
        return serviceRepository.findAll();
    }
}