package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
