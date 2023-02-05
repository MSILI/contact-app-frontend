package com.app.contacts.utils;

import com.app.contacts.entities.Contact;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CsvUtils {

    public String TYPE = "text/csv";
    public String[] HEADERS = {"id", "fistname", "lastname", "birthday", "address", "email", "phone"};

    public boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Contact> csvToContactList(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             final CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Contact> contacts = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord: csvRecords) {
                contacts.add(
                        Contact.builder()
                                .firstname(csvRecord.get("firstname"))
                                .lastname(csvRecord.get("lastname"))
                                .birthDay(parseFromString(csvRecord.get("birthday")))
                                .address(csvRecord.get("address"))
                                .email(csvRecord.get("email"))
                                .phone(csvRecord.get("phone"))
                                .build()
                );
            }
            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("Erreur de parse de fichier CSV !" + e.getMessage());
        }
    }

    private Instant parseFromString(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
    }

}
