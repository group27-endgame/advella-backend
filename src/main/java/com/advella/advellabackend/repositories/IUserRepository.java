package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();

    @Query(value = "SELECT * FROM Users WHERE users_id IN (SELECT users_id FROM Users_Roles WHERE role_id = :roleId)", nativeQuery = true)
    List<User> getUsersByRole(Integer roleId);

    @Query(value = "SELECT TOP (:amount) * FROM Users ORDER BY users_id DESC", nativeQuery = true)
    List<User> getLatestUsers(int amount);

    @Query(value = "SELECT * FROM Users WHERE user_location = :location", nativeQuery = true)
    List<User> getUsersByLocation(String location);

    @Query(value = "SELECT COUNT(*) FROM Users WHERE registration_datetime >= :fromDate AND registration_datetime <= :toDate", nativeQuery = true)
    Integer registeredUsers(Date fromDate, Date toDate);

    @Query(value = "SELECT * FROM Users WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);
}
