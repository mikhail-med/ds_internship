package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Task;

import java.util.List;

/**
 * Сервис для задач
 */
public interface TaskService {
    List<Task> getAll();

    Task getById(int id);

    Task save(Task task);

    Task update(int id, Task task);

    void delete(int id);
}
