package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.repositories.IContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {
    private final IContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Integer getUnseenContacts() {
        return contactRepository.getUnseenContacts();
    }

    public void deleteContact(Integer contactId) {
        contactRepository.deleteById(contactId);
    }

    public void setAllUnseenContactsToSeen() {
        contactRepository.setAllUnseenToSeen();
    }
}
