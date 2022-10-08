package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContactRepository extends JpaRepository<Contact, Integer> {
    @Override
    List<Contact> findAll();
}
