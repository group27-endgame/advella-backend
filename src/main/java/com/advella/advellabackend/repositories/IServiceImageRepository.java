package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.ServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceImageRepository extends JpaRepository<Integer, ServiceImage> {
}
