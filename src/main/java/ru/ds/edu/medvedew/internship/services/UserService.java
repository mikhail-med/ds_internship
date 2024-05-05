package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;

import java.util.List;

/**
 * Сервис для пользователей
 */
public interface UserService {
    List<User> getAll();

    User getById(int id);

    User save(User user);

    User update(int id, User user);

    void delete(int id);

    /**
     * @param userId - id пользователя
     * @return все сообщения отправленные и полученные пользователем
     */
    List<Message> getAllMessagesForUser(int userId);

    /**
     * @param userId - id пользователя
     * @return все сообщения отправленные пользователем
     */
    List<Message> getAllMessagesSentByUser(int userId);

    /**
     * @param userId - id пользователя
     * @return все сообщения полученные пользователем
     */
    List<Message> getAllMessageReceivedByUser(int userId);

}
