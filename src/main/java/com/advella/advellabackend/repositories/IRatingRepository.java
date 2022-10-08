package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Integer> {
    @Override
    List<Rating> findAll();
}
