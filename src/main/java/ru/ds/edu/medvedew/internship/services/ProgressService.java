package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.dto.LessonWithTasksDto;
import ru.ds.edu.medvedew.internship.dto.UserWithTasksDto;

import java.util.List;

/**
 * Сервис для формирования ведомостей/ отчётов по успеваемоти
 */
public interface ProgressService {
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
