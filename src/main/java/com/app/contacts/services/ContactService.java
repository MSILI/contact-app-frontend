package com.app.contacts.services;

import com.app.contacts.entities.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAllByFirstnameOrLastname(String query);

    Contact findById(Long id);

    Contact save(final Contact contact);

    Contact update(final Contact contact);

    void deleteById(Long id);
}
