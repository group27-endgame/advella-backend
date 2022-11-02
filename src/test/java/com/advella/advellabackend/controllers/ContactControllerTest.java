package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.repositories.IContactRepository;
import com.advella.advellabackend.security.SecurityConfig;
import com.advella.advellabackend.security.filter.CustomAuthenticationFilter;
import com.advella.advellabackend.security.filter.CustomAuthorizationFilter;
import com.advella.advellabackend.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContactRepository contactRepository;

    Contact CONTACT_1 = new Contact(1, new Date(1667390291L), "Contact one", false, null);
    Contact CONTACT_2 = new Contact(2, new Date(1667390295L), "Contact two", false, null);
    Contact CONTACT_3 = new Contact(3, new Date(1667390298L), "Contact three", true, null);

    @Test
    void getAllContacts_Multiple() throws Exception {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2, CONTACT_3));

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].content", Matchers.is("Contact one")))
                .andExpect(jsonPath("$[1].content", Matchers.is("Contact two")))
                .andExpect(jsonPath("$[2].content", Matchers.is("Contact three")));
    }

    @Test
    void getAllContacts_Empty() throws Exception {
        List<Contact> contacts = new ArrayList<>();

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getUnseenContactNumber_Multiple() throws Exception {
        List<Contact> contacts = new ArrayList<>(Arrays.asList(CONTACT_1, CONTACT_2));

        Mockito.when(contactRepository.getUnseenContacts()).thenReturn(contacts.size());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/contacts/dash-board/unseen")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(2)));
    }

    @Test
    void getUnseenContactNumber_Empty() throws Exception {
        List<Contact> contacts = new ArrayList<>();

        Mockito.when(contactRepository.getUnseenContacts()).thenReturn(contacts.size());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/contacts/dash-board/unseen")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(0)));
    }

    @Test
    void setUnseenContactsToSeen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/contacts/dash-board/seen")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContact_Success() throws Exception {
        Mockito.when(contactRepository.getReferenceById(CONTACT_1.getContactId())).thenReturn(CONTACT_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/contacts/dash-board/"+CONTACT_1.getContactId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContact_Failure() throws Exception {
        Mockito.when(contactRepository.getReferenceById(CONTACT_1.getContactId())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/contacts/dash-board/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}