package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ds.edu.medvedew.internship.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("from Message m where (m.sender.id = :firstUserId and m.consumer.id = :secondUserId) " +
            "or (m.sender.id = :secondUserId and m.consumer.id = :firstUserId)")
    List<Message> findAllBetweenUsers(int firstUserId, int secondUserId);
}
