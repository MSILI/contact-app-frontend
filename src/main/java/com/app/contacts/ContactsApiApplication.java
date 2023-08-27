package com.app.contacts;

import com.app.contacts.entities.Role;
import com.app.contacts.entities.User;
import com.app.contacts.entities.enumeration.RoleName;
import com.app.contacts.utils.DataInitialazer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {ContactsApiApplication.class, Jsr310JpaConverters.class})
public class ContactsApiApplication implements CommandLineRunner {

    private final DataInitialazer dataInitialazer;

    public ContactsApiApplication(DataInitialazer dataInitialazer) {
        this.dataInitialazer = dataInitialazer;
    }


    public static void main(String[] args) {
        SpringApplication.run(ContactsApiApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void run(String... args) throws Exception {
        dataInitialazer.initRolesList();
        dataInitialazer.initAdminAccount();
    }
}
