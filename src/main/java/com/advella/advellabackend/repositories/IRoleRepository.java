package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    @Override
    List<Role> findAll();
}
