package com.app.contacts.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface CsvService {
    void upload(MultipartFile csvFile);

    ByteArrayInputStream download();
}
