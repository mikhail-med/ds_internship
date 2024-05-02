package ru.ds.edu.medvedew.internship.services;

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
}
