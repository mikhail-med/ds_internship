package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Message;

import java.util.List;

/**
 * Сервис для сообщений
 */
public interface MessageService {
    List<Message> getAll();

    Message getById(int id);

    Message save(Message message);

    Message update(int id, Message message);

    void delete(int id);
}
