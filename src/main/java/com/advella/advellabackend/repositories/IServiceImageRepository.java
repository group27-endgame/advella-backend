package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceImageRepository extends JpaRepository<ServiceImage, Integer> {
}
