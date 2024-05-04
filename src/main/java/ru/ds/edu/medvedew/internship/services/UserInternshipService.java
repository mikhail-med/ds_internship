package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;

import java.util.List;

/**
 * Сервис для стажировок пользователя
 */
public interface UserInternshipService {
    List<UserInternship> getAll();

    UserInternship getById(int id);

    UserInternship save(UserInternship userInternship);

    UserInternship update(int id, UserInternship userInternship);

    void delete(int id);

    /**
     * @return все записи с конкретным статусом
     */
    List<UserInternship> getAllWithStatus(UserInternshipStatus userInternshipStatus);


    /**
     * @param internshipId - id стажировки
     * @return все записи с  конкретным статусом для конретной стажировки
     */
    List<UserInternship> getAllWithStatusOnInternship(UserInternshipStatus status, int internshipId);
}
