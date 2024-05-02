package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.repositories.LessonRepository;
import ru.ds.edu.medvedew.internship.services.LessonService;

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
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Lesson with id %d doesn't exists", id));
        }
        lesson.setId(id);
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void delete(int id) {
        lessonRepository.deleteById(id);
    }
}
