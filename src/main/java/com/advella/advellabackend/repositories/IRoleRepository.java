package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    @Override
    List<Role> findAll();

    @Query(value = "SELECT * FROM Roles WHERE role_name = :name", nativeQuery = true)
    Role findByRoleName(String name);
}
