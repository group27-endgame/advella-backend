package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
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

    @Query(value = "SELECT COUNT(*) FROM Task_Services WHERE service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    int getServiceCount(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(service_money_amount) FROM Task_Services WHERE service_deadline >= GETDATE() AND service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    int getClosedServiceTotalValue(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(service_money_amount) FROM Task_Services WHERE service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    int getServiceTotalValue(Date fromDate, Date toDate);
}
