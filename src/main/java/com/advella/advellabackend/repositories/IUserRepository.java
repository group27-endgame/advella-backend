package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();

    @Query(value = "SELECT TOP (:amount) * FROM Users ORDER BY users_id DESC", nativeQuery = true)
    List<User> getFiveLatestUsers(int amount);

    @Query(value = "SELECT * FROM Users WHERE user_location = :location", nativeQuery = true)
    List<User> getUsersByLocation(String location);

    @Query(value = "SELECT * FROM Users WHERE registration_datetime BETWEEN :fromDate AND :toDate ORDER BY registration_datetime DESC", nativeQuery = true)
    int registeredUsers(Date fromDate, Date toDate);
}
