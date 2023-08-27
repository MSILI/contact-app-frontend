package com.app.contacts.utils;

import com.app.contacts.entities.Contact;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@UtilityClass
public class CsvUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public String TYPE = "text/csv";
    public String[] HEADERS = {"id", "firstname", "lastname", "birthday", "address", "email", "phone"};

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<Contact> csvToContactList(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             final CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Contact> contacts = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                contacts.add(
                        Contact.builder()
                                .firstname(csvRecord.get("firstname"))
                                .lastname(csvRecord.get("lastname"))
                                .birthDay(parseDateFromString(csvRecord.get("birthday")))
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

    private Instant parseDateFromString(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT))
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
    }

    public ByteArrayInputStream contactsToCSV(List<Contact> contacts) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL).withHeader(HEADERS);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Contact contact : contacts) {
                List<String> data = Arrays.asList(
                        String.valueOf(contact.getId()),
                        contact.getFirstname(),
                        contact.getLastname(),
                        parseDateToString(contact.getBirthDay()),
                        contact.getAddress(),
                        contact.getEmail(),
                        contact.getPhone()
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erreur de parse de fichier CSV : " + e.getMessage());
        }
    }

    private String parseDateToString(Instant date) {
        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern(DATE_FORMAT)
                        .withLocale(Locale.FRANCE)
                        .withZone(ZoneId.systemDefault());
        return formatter.format(date);
    }

}
