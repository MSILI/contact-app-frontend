package com.app.contacts.services;

import org.springframework.web.multipart.MultipartFile;

public interface CsvService {
    void upload(MultipartFile csvFile);
}
