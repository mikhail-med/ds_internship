package ru.ds.edu.medvedew.internship.services;

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

}
