package com.app.contacts.services;

import com.app.contacts.entities.Contact;
import com.app.contacts.exceptions.ExistsException;
import com.app.contacts.exceptions.NotFoundException;
import com.app.contacts.repositories.ContactRepository;
import com.app.contacts.services.impl.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact expectedContact;

    @BeforeEach
    void setUp() {
        this.expectedContact = Contact
                .builder()
                .id(1L)
                .firstname("John")
                .lastname("DOE")
                .birthDay(Instant.parse("1993-10-24T06:30:24.00Z"))
                .address("89 rue de Richelieu 75002 Paris")
                .phone("0669095238")
                .email("john.doe@mail.com")
                .build();
    }

    @Test
    void findAllByFirstnameOrLastnameOkTest() {
        Mockito.when(this.contactRepository.findAllByFirstnameOrLastnameContainingIgnoreCase(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(Collections.singletonList(this.expectedContact));
        final List<Contact> contacts = this.contactService.findAllByFirstnameOrLastname("John");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(contacts),
                () -> Assertions.assertFalse(contacts.isEmpty()),
                () -> Assertions.assertEquals(expectedContact.getFirstname(), contacts.get(0).getFirstname()),
                () -> Assertions.assertEquals(expectedContact.getLastname(), contacts.get(0).getLastname()),
                () -> Assertions.assertEquals(expectedContact.getBirthDay(), contacts.get(0).getBirthDay()),
                () -> Assertions.assertEquals(expectedContact.getAddress(), contacts.get(0).getAddress()),
                () -> Assertions.assertEquals(expectedContact.getEmail(), contacts.get(0).getEmail()),
                () -> Assertions.assertEquals(expectedContact.getPhone(), contacts.get(0).getPhone())
        );
    }

    @Test
    void findByIdOkTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(this.expectedContact));
        final Contact actualContact = this.contactService.findById(1L);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(actualContact),
                () -> Assertions.assertEquals(expectedContact.getId(), actualContact.getId()),
                () -> Assertions.assertEquals(expectedContact.getFirstname(), actualContact.getFirstname()),
                () -> Assertions.assertEquals(expectedContact.getLastname(), actualContact.getLastname()),
                () -> Assertions.assertEquals(expectedContact.getBirthDay(), actualContact.getBirthDay()),
                () -> Assertions.assertEquals(expectedContact.getAddress(), actualContact.getAddress()),
                () -> Assertions.assertEquals(expectedContact.getEmail(), actualContact.getEmail()),
                () -> Assertions.assertEquals(expectedContact.getPhone(), actualContact.getPhone())
        );
    }

    @Test
    void findByIdNotFoundTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        final Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> this.contactService.findById(1L));
        Assertions.assertEquals("Le contact avec l'id 1 est introuvable!", exception.getMessage());
    }

    @Test
    void saveOkTest() {
        Mockito.when(this.contactRepository.existsByPhone(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(this.contactRepository.existsByEmail(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(this.contactRepository.save(ArgumentMatchers.any()))
                .thenReturn(this.expectedContact);
        final Contact actualContact = this.contactService.save(expectedContact);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(actualContact),
                () -> Assertions.assertEquals(expectedContact.getId(), actualContact.getId()),
                () -> Assertions.assertEquals(expectedContact.getFirstname(), actualContact.getFirstname()),
                () -> Assertions.assertEquals(expectedContact.getLastname(), actualContact.getLastname()),
                () -> Assertions.assertEquals(expectedContact.getBirthDay(), actualContact.getBirthDay()),
                () -> Assertions.assertEquals(expectedContact.getAddress(), actualContact.getAddress()),
                () -> Assertions.assertEquals(expectedContact.getEmail(), actualContact.getEmail()),
                () -> Assertions.assertEquals(expectedContact.getPhone(), actualContact.getPhone())
        );
    }
    @Test
    void saveEmailExistsTest() {
        Mockito.when(this.contactRepository.existsByEmail(ArgumentMatchers.anyString()))
                .thenReturn(true);
        final Throwable exception = Assertions.assertThrows(ExistsException.class, () -> this.contactService.save(this.expectedContact));
        Assertions.assertEquals("Le contact avec l'email john.doe@mail.com existe déjà!", exception.getMessage());
    }

    @Test
    void savePhoneExistsTest() {
        Mockito.when(this.contactRepository.existsByEmail(ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mockito.when(this.contactRepository.existsByPhone(ArgumentMatchers.anyString()))
                .thenReturn(true);
        final Throwable exception = Assertions.assertThrows(ExistsException.class, () -> this.contactService.save(this.expectedContact));
        Assertions.assertEquals("Le contact avec le numéro de téléphone 0669095238 existe déjà!", exception.getMessage());
    }

    @Test
    void updateOkTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(this.expectedContact));
        Mockito.when(this.contactRepository.save(ArgumentMatchers.any()))
                .thenReturn(this.expectedContact);
        final Contact actualContact = this.contactService.update(expectedContact);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(actualContact),
                () -> Assertions.assertEquals(expectedContact.getId(), actualContact.getId()),
                () -> Assertions.assertEquals(expectedContact.getFirstname(), actualContact.getFirstname()),
                () -> Assertions.assertEquals(expectedContact.getLastname(), actualContact.getLastname()),
                () -> Assertions.assertEquals(expectedContact.getBirthDay(), actualContact.getBirthDay()),
                () -> Assertions.assertEquals(expectedContact.getAddress(), actualContact.getAddress()),
                () -> Assertions.assertEquals(expectedContact.getEmail(), actualContact.getEmail()),
                () -> Assertions.assertEquals(expectedContact.getPhone(), actualContact.getPhone())
        );
    }

    @Test
    void updateContactNotFoundTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        final Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> this.contactService.update(this.expectedContact));
        Assertions.assertEquals("Le contact avec l'id 1 est introuvable!", exception.getMessage());
    }

    @Test
    void deleteByIdOkTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(this.expectedContact));
        this.contactService.deleteById(1L);
    }

    @Test
    void deleteByIdContactNotFoundTest() {
        Mockito.when(this.contactRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        final Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> this.contactService.deleteById(1L));
        Assertions.assertEquals("Le contact avec l'id 1 est introuvable!", exception.getMessage());
    }
}