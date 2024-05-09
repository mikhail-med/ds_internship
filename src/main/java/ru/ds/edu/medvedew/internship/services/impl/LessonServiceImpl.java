package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.repositories.LessonRepository;
import ru.ds.edu.medvedew.internship.services.LessonService;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getById(int id) {
        return lessonRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Lesson with id %d not found", id)));
    }

    @Transactional
    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public Lesson update(int id, Lesson lesson) {
        checkLessonExists(id);
        lesson.setId(id);
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void delete(int id) {
        checkLessonExists(id);
        lessonRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasksForLesson(int lessonId) {
        Lesson lesson = getById(lessonId);
        return new ArrayList<>(lesson.getTask());
    }

    private void checkLessonExists(int lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException(String.format("Lesson with id %d doesn't exists", lessonId));
        }
    }
}
