package com.app.contacts.repositories;

import com.app.contacts.entities.Role;
import com.app.contacts.entities.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Boolean existsByRoleName(RoleName roleName);

    Optional<Role> findByRoleName(RoleName roleName);
}
