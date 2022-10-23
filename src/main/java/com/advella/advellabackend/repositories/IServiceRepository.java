package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IServiceRepository extends JpaRepository<Service,Integer> {
    @Override
    List<Service> findAll();

    @Query(value = "SELECT TOP (:amount) * FROM Task_Services ORDER BY service_id DESC", nativeQuery = true)
    List<Service> getFiveLatestServices(int amount);

    @Query(value = "SELECT * FROM Task_Services WHERE service_location = :location", nativeQuery = true)
    List<Service> getServicesByLocation(String location);

    @Query(value = "SELECT COUNT(*) FROM Task_Services", nativeQuery = true)
    int getServiceCount();
}
