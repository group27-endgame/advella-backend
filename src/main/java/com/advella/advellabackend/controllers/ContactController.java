package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Contact;
import com.advella.advellabackend.services.ContactService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContactController {
    private final ContactService contactService;

    @ApiOperation(value = "Get all contacts", notes = "Gets list of all contacts")
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @ApiOperation(value = "Get unseen product number", notes = "Gets count of all unseen contacts")
    @GetMapping("/contacts/dash-board/unseen")
    public ResponseEntity<Integer> getUnseenContactNumber() {
        return ResponseEntity.ok(contactService.getUnseenContacts());
    }

    @ApiOperation(value = "Set unseen contacts to seen", notes = "Sets all the unseen contacts to seen")
    @PostMapping("/contacts/dash-board/seen")
    public ResponseEntity<Void> setUnseenContactsToSeen() {
        contactService.setAllUnseenContactsToSeen();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete contact", notes = "Deletes contact by Id")
    @DeleteMapping("/contacts/dash-board/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Integer contactId) {
        return contactService.deleteContact(contactId);
    }
}
