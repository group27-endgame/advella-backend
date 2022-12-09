package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {
    private final IContactRepository contactRepository;
    private final UserService userService;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Integer getUnseenContacts() {
        return contactRepository.getUnseenContacts();
    }

    public ResponseEntity<Void> deleteContact(Integer contactId) {
        if (contactRepository.getReferenceById(contactId) == null) {
            return ResponseEntity.notFound().build();
        }
        contactRepository.deleteById(contactId);
        return ResponseEntity.ok().build();
    }

    public void postContact(String token, Contact contact)
    {
        User user = userService.getUserFromHeader(token);
        contact.setContactUser(user);
        contact.setMessageDateTime(new Date(new java.util.Date().getTime()));

        contactRepository.save(contact);
    }

    public void setAllUnseenContactsToSeen() {
        contactRepository.setAllUnseenToSeen();
    }
}
