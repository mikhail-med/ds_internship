package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ds.edu.medvedew.internship.models.UserTask;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {
    @Query("from UserTask ut where ut.task.lesson.id = :lessonId")
    List<UserTask> findAllByLessonId(int lessonId);
}
