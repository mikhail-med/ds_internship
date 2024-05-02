package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
