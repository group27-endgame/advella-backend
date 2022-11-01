package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/contacts/dash-board/unseen")
    public ResponseEntity<Integer> getUnseenContactNumber() {
        return ResponseEntity.ok(contactService.getUnseenContacts());
    }

    @PostMapping("/contacts/dash-board/seen")
    public ResponseEntity<Void> setUnseenContactsToSeen() {
        contactService.setAllUnseenContactsToSeen();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/contacts/dash-board/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Integer contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }
}
