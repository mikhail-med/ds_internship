package ru.ds.edu.medvedew.internship.services.gitlab;

/**
 * Сервис для работы с gitlab
 */
public interface GitlabService {
    /**
     * Должны быть созданы репозитории всех задач занятия для каждого активного
     * участника стажировки.
     * Т.е. создан fork эталонного репозитория.
     * В случае ошибки необходимо создать сообщение для всех пользователей с ролью admin.
     *
     * @param lessonId     - id занятия
     * @param privateToken - token для доступа к gitlab
     */
    void publishLesson(int lessonId, String privateToken);

    /**
     * Создание аккаунта пользователя в gitlab.
     * До пользователя (для которого создаётся аккаунт) должен быть доведён пароль / ссылка.
     * Реализация сама определяет какаим образом пользователь получит пароль, но это должно произойти.
     *
     * @param userId       - id пользователя стажировки
     * @param privateToken - token для доступа к gitlab
     */
    void createGitlabAccountForUser(int userId, String privateToken);
}
