package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
