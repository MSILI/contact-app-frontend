package com.app.contacts.repositories;

import com.app.contacts.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    List<Contact> findAllByFirstnameOrLastnameContainingIgnoreCase(String firstname, String lastName);
}
