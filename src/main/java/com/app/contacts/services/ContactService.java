package com.app.contacts.services;

import com.app.contacts.entities.Contact;
import com.app.contacts.repositories.ContactRepository;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(final Contact contact);

    Contact update(final Contact contact);

    void deleteById(Long id);
}
