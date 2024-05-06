package ru.ds.edu.medvedew.internship.services.gitlab;

/**
 * Компонент для отправки запросов к gitlab.
 */
public interface GitlabClient {

    /**
     * @param projectPathWithNamespace - (namespace пользователя/имя поекта) путь к проекту от которого делаем fork, например - 'user/project'
     * @param toNamespace              - namespace (username) пользователя gitlab для которого делаем fork
     * @param privateToken             - private token для доступа к gitlab
     * @return true - если выполнено успешно, false - если произошла ошибка
     */
    boolean forkProject(String projectPathWithNamespace, String toNamespace, String privateToken);

    /**
     * Создание gitlab пользователя
     *
     * @param name
     * @param username
     * @param email
     * @param password
     * @param privateToken- private token для доступа к gitlab
     * @return true - если выполнено успешно, false - если произошла ошибка
     */
    boolean createUser(String name, String username, String email, String password, String privateToken);
}
