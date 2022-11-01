package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IContactRepository extends JpaRepository<Contact, Integer> {
    @Override
    List<Contact> findAll();

    @Query(value = "SELECT COUNT(*) FROM Contact_Us WHERE seen = 0", nativeQuery = true)
    Integer getUnseenContacts();

    @Modifying
    @Query(value = "UPDATE Contact_Us SET seen = 1")
    void setAllUnseenToSeen();
}
