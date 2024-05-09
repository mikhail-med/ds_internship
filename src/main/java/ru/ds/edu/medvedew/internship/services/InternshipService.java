package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.dto.LessonWithTasksDto;
import ru.ds.edu.medvedew.internship.dto.UserWithTasksDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.User;

import java.util.List;

/**
 * Сервис для стажировок
 */
public interface InternshipService {
    List<Internship> getAll();

    Internship getById(int id);

    Internship save(Internship internship);

    Internship update(int id, Internship internship);

    void delete(int id);

    /**
     * @param internshipId - id стажировки
     * @return все участники стажировки с id = internshipId
     */
    List<User> getAllParticipants(int internshipId);

    /**
     * Подать заявку пользователя на участие в стажировке,
     * после вызова этого метода должно считаться, что
     * пользователь с id = userId SUBMITTED_APPLICATION на
     * стажировку с id = internshipId
     *
     * @param userId       - id пользователя
     * @param internshipId - id стажировки
     */
    void addUserToInternship(int userId, int internshipId);

    /**
     * @param internshipId - id стажировки
     * @return все занятия стажировки с id = internshipId
     */
    List<Lesson> getAllLessons(int internshipId);

    /**
     * Получить успеваемость пользователя для стажировки.
     * Успеваемость включает в себя список занятий стажировки
     * у которых есть список задач со статусом решения пользователя
     *
     * @param internshipId - id стажировки
     * @param userId       - id пользователя
     * @return список занятий с задачами и их статусом решения
     */
    List<LessonWithTasksDto> getInternshipProgressForUser(int internshipId, int userId);

    /**
     * Получить ведомость по стажировке.
     * Ведомость включает в себя пользователя и список результатов проверок задач для него.
     *
     * @param internshipId - id стажировки
     * @return список результатов проверок задач для участников стажировки
     */
    List<UserWithTasksDto> getInternshipProgress(int internshipId);
}
