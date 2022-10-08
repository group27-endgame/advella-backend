package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
        @Override
        List<User> findAll();
}
