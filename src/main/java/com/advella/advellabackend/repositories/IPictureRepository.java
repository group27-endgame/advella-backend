package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPictureRepository extends JpaRepository<Picture, Integer> {
}
