package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
        @Override
        List<User> findAll();

        @Query(value = "SELECT * FROM Users ORDER BY users_id DESC LIMIT 5", nativeQuery = true)
        List<User> getFiveLatestUsers();

        @Query(value = "SELECT * FROM Users WHERE user_location = :location", nativeQuery = true)
        List<User> getUsersByLocation(String location);
}
