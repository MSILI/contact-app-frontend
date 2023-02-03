package com.app.contacts.controllers;

import com.app.contacts.entities.Contact;
import com.app.contacts.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    private static final String RESOURCE_URI = "/api/contacts/";
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> findById(@PathVariable("id") Long id) throws URISyntaxException {
        final Contact contact = contactService.findById(id);
        return ResponseEntity.ok()
                .location(new URI(RESOURCE_URI + contact.getId()))
                .body(contact);
    }

    @PostMapping
    public ResponseEntity<Contact> save(@RequestBody Contact contact) throws URISyntaxException {
        final Contact savedContact = contactService.save(contact);
        return ResponseEntity.created(new URI(RESOURCE_URI + savedContact.getId()))
                .body(savedContact);
    }

    @PutMapping
    public ResponseEntity<Contact> update(@RequestBody Contact contact) throws URISyntaxException {
        final Contact updatedContact = contactService.update(contact);
        return ResponseEntity.ok()
                .location(new URI(RESOURCE_URI + updatedContact.getId()))
                .body(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        contactService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
