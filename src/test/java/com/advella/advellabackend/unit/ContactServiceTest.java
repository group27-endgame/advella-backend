package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.repositories.IContactRepository;
import com.advella.advellabackend.services.ContactService;
import com.advella.advellabackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ContactServiceTest {
    private IContactRepository contactRepository;
    private UserService userService;

    private ContactService contactService;

    Contact CONTACT_1 = new Contact(1, new Date(1667390291L), "Contact one", false, null);
    Contact CONTACT_2 = new Contact(2, new Date(1667390295L), "Contact two", false, null);
    Contact CONTACT_3 = new Contact(3, new Date(1667390298L), "Contact three", true, null);

    @BeforeEach
    void setUp() {
        contactRepository = Mockito.mock(IContactRepository.class);
        userService = Mockito.mock(UserService.class);
        contactService = new ContactService(contactRepository, userService);
    }

    @Test
    void getAllContacts() throws Exception {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2, CONTACT_3));

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> results = contactService.getAllContacts();

        assertTrue(results.size() == 3);
        assertEquals(CONTACT_1, results.get(0));
        assertEquals(CONTACT_2, results.get(1));
        assertEquals(CONTACT_3, results.get(2));
    }

    @Test
    void getUnseenContacts() {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2, CONTACT_3));

        Mockito.when(contactRepository.getUnseenContacts()).thenReturn(contacts.size());

        Integer count = contactService.getUnseenContacts();

        assertEquals(3, count);
    }

    @Test
    void deleteContact_Success() {
        Mockito.when(contactRepository.getReferenceById(1)).thenReturn(CONTACT_1);

        ResponseEntity<Void> response = contactService.deleteContact(1);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void deleteContact_Failure() {
        Mockito.when(contactRepository.getReferenceById(1)).thenReturn(null);

        ResponseEntity<Void> response = contactService.deleteContact(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}