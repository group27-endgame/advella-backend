package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IServiceRepository extends JpaRepository<Service,Integer> {
    @Override
    List<Service> findAll();

    @Query(value = "SELECT TOP (:amount) * FROM Task_Services ORDER BY service_posted_datetime DESC", nativeQuery = true)
    List<Service> getLatestServices(int amount);

    @Query(value = "SELECT * FROM Task_Services WHERE service_location = :location", nativeQuery = true)
    List<Service> getServicesByLocation(String location);

    @Query(value = "SELECT COUNT(*) FROM Task_Services", nativeQuery = true)
    Integer getServiceCount();

    @Query(value = "SELECT COUNT(*) FROM Task_Services WHERE service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getServiceCount(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(service_money_amount) FROM Task_Services WHERE service_status = 'closed' AND service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getClosedServiceTotalValue(Date fromDate, Date toDate);

    @Query(value = "SELECT SUM(service_money_amount) FROM Task_Services WHERE service_posted_datetime BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Integer getServiceTotalValue(Date fromDate, Date toDate);

    List<Service> findByTitleContaining(String title);

    @Query(value = "SELECT * FROM Task_Services WHERE category_id = :categoryId", nativeQuery = true)
    List<Service> getServicesWithCategory(Integer categoryId);

    @Query(value = "SELECT TOP (:amount) * FROM Task_Services WHERE users_id = :userId ORDER BY service_posted_datetime DESC", nativeQuery = true)
    List<Service> getServicesPostedByUser(Integer userId, int amount);
}
