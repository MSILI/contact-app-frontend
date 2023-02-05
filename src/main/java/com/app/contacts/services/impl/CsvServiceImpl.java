package com.app.contacts.services.impl;

import com.app.contacts.repositories.ContactRepository;
import com.app.contacts.services.CsvService;
import com.app.contacts.utils.CsvUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class CsvServiceImpl implements CsvService {

    private final ContactRepository contactRepository;

    public CsvServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void upload(MultipartFile csvFile) {
        try {
            contactRepository.saveAll(CsvUtils.csvToContactList(csvFile.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Fail to store csv data " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream download() {
        return CsvUtils.contactsToCSV(this.contactRepository.findAll());
    }
}
