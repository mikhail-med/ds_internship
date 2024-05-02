package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
