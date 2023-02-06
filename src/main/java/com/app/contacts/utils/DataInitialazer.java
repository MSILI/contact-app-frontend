package com.app.contacts.utils;

import com.app.contacts.entities.Role;
import com.app.contacts.entities.User;
import com.app.contacts.entities.enumeration.RoleName;
import com.app.contacts.repositories.RoleRepository;
import com.app.contacts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitialazer {

    @Value("${app.defaultAccount.username}")
    private String defaultUsername;
    @Value("${app.defaultAccount.password}")
    private String defaultPassword;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitialazer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initRolesList() {
        if (!roleRepository.existsByRoleName(RoleName.ROLE_USER)) {
            roleRepository.save(
                    Role.builder()
                            .id(1L)
                            .roleName(RoleName.ROLE_USER)
                            .build()
            );
        }
        if (!roleRepository.existsByRoleName(RoleName.ROLE_ADMIN)) {
            roleRepository.save(
                    Role.builder()
                            .id(2L)
                            .roleName(RoleName.ROLE_ADMIN)
                            .build()
            );
        }
    }

    public void initAdminAccount() {
        if (!userRepository.existsByUsername(defaultUsername)) {
            Role role = roleRepository.findByRoleName(RoleName.ROLE_ADMIN).get();
            User user = User
                    .builder()
                    .id(1L)
                    .username(defaultUsername)
                    .password(passwordEncoder.encode(defaultPassword))
                    .listOfRoles(Collections.singleton(role))
                    .build();
            userRepository.save(user);
        }
    }
}
