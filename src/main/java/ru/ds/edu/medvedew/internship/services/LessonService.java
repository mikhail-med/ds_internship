package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Lesson;

import java.util.List;

/**
 * Сервис для занятий
 */
public interface LessonService {
    List<Lesson> getAll();

    Lesson getById(int id);

    Lesson save(Lesson lesson);

    Lesson update(int id, Lesson lesson);

    void delete(int id);
}
