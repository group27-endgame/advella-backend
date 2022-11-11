package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.repositories.IContactRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContactServiceTest {
    private IContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    Contact CONTACT_1 = new Contact(1, new Date(1667390291L), "Contact one", false, null);
    Contact CONTACT_2 = new Contact(2, new Date(1667390295L), "Contact two", false, null);
    Contact CONTACT_3 = new Contact(3, new Date(1667390298L), "Contact three", true, null);

    @BeforeEach
    void setUp() {
        contactRepository = Mockito.mock(IContactRepository.class);
        contactService = new ContactService(contactRepository);
    }

    @Test
    void getAllContacts() throws Exception {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2, CONTACT_3));

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> results = contactService.getAllContacts();

        assertTrue(results.size() == 3);
        assertEquals(results.get(0), CONTACT_1);
        assertEquals(results.get(1), CONTACT_2);
        assertEquals(results.get(2), CONTACT_3);
    }

    @Test
    void getUnseenContacts() {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2, CONTACT_3));

        Mockito.when(contactRepository.getUnseenContacts()).thenReturn(contacts.size());

        Integer count = contactService.getUnseenContacts();

        assertTrue(count == 3);
    }

    @Test
    void deleteContact_Success() {
        Mockito.when(contactRepository.getReferenceById(1)).thenReturn(CONTACT_1);

        ResponseEntity<Void> response = contactService.deleteContact(1);

        assertEquals(response, ResponseEntity.ok().build());
    }

    @Test
    void deleteContact_Failure() {
        Mockito.when(contactRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = contactService.deleteContact(1);

        assertEquals(response, ResponseEntity.notFound().build());
    }
}