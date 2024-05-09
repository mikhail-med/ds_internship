package ru.ds.edu.medvedew.internship.services.gitlab;

import ru.ds.edu.medvedew.internship.dto.gitlab.GitlabCommit;
import ru.ds.edu.medvedew.internship.exceptions.checked.gitlab.GitlabClientException;

import java.util.List;

/**
 * Компонент для отправки запросов к gitlab.
 */
public interface GitlabClient {

    /**
     * @param projectPathWithNamespace - (namespace пользователя/имя поекта) путь к проекту от которого делаем fork, например - 'user/project'
     * @param toNamespace              - namespace (username) пользователя gitlab для которого делаем fork
     * @param privateToken             - private token для доступа к gitlab
     * @throws GitlabClientException - если произошла ошибка
     */
    void forkProject(String projectPathWithNamespace, String toNamespace, String privateToken)
            throws GitlabClientException;

    /**
     * Создание gitlab пользователя
     *
     * @param name
     * @param username
     * @param email
     * @param password
     * @param privateToken- private token для доступа к gitlab
     * @throws GitlabClientException - если произошла ошибка
     */
    void createUser(String name, String username, String email, String password, String privateToken)
            throws GitlabClientException;

    /**
     * @param projectPathWithNamespace - путь к проекту (пользователь/имя поекта), например - 'user/project'
     * @param privateToken             - private token для доступа к gitlab
     * @return список коммитов
     * @throws GitlabClientException - если произошла ошибка
     */
    List<GitlabCommit> getCommitsForProject(String projectPathWithNamespace, String privateToken)
            throws GitlabClientException;
}
