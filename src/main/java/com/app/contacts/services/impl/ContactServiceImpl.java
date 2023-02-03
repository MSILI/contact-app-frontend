package com.app.contacts.services.impl;

import com.app.contacts.entities.Contact;
import com.app.contacts.exceptions.ExistsException;
import com.app.contacts.exceptions.NotFoundException;
import com.app.contacts.repositories.ContactRepository;
import com.app.contacts.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return this.contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (!contactOptional.isPresent()) {
            throw new NotFoundException("Le contact avec l'id " + id + " est introuvable!");
        }
        return contactOptional.get();
    }

    @Override
    public Contact save(Contact contact) {
        if (!contactRepository.existsByEmail(contact.getEmail())) {
            throw new ExistsException("Le contact avec l'email " + contact.getEmail() + " existe déjà!");
        }
        if (!contactRepository.existsByPhone(contact.getPhone())) {
            throw new ExistsException("Le contact avec le numéro de téléphone " + contact.getPhone() + " existe déjà!");
        }
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return contactRepository.findById(contact.getId())
                .map(storedContact -> {
                    storedContact.setFirstname(contact.getFirstname());
                    storedContact.setLastname(contact.getLastname());
                    storedContact.setEmail(contact.getEmail());
                    storedContact.setPhone(contact.getPhone());
                    storedContact.setAddress(contact.getAddress());
                    storedContact.setBirthDay(contact.getBirthDay());
                    return contactRepository.save(storedContact);
                }).orElseThrow(() -> new NotFoundException("Le contact avec l'id " + contact.getId() + " est introuvable!"));
    }

    @Override
    public void deleteById(Long id) {
        if (!contactRepository.findById(id).isPresent()) {
            throw new NotFoundException("Le contact avec l'id " + id + " est introuvable!");
        }
        contactRepository.deleteById(id);
    }
}
