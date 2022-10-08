package com.advella.advellabackend.services;

import com.advella.advellabackend.model.ServiceCategory;
import com.advella.advellabackend.repositories.IServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCategoryService {
    private final IServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategory> getServiceCategories() {
        return serviceCategoryRepository.findAll();
    }
}
