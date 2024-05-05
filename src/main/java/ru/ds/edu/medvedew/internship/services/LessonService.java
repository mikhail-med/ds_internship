package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Task;

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

    /**
     * @param lessonId - id занятия
     * @return все задачи этого занятия
     */
    List<Task> getAllTasksForLesson(int lessonId);
}
