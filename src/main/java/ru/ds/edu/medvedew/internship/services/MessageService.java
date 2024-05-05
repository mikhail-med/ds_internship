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

    /**
     * Возвращает все сообщения между двумя пользователями.
     * И первый и второй пользователь могут быть как отправителем, так и получателем
     *
     * @param firstUserId  - id одного пользователя
     * @param secondUserId - id другого польщователя
     * @return все сообщения между двумя пользователями
     */
    List<Message> getAllMessagesBetweenUsers(int firstUserId, int secondUserId);

    /**
     * Создаёт сообщение от одного пользователя - другому пользователю.
     *
     * @param senderId  - id отправителя
     * @param consumerId - id получателя
     * @param text         - содерджимое сообщения
     * @return созданное сообщение
     */
    Message sendMessageFromUserToUserWithText(int senderId, int consumerId, String text);
}
