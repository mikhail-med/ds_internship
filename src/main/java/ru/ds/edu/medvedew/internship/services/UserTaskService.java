package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.UserTask;

import java.util.List;

/**
 * Сервис для результата проверки задачи
 */
public interface UserTaskService {
    List<UserTask> getAll();

    UserTask getById(int id);

    UserTask save(UserTask userTask);

    UserTask update(int id, UserTask userTask);

    void delete(int id);
}
