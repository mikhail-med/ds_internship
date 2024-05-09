package ru.ds.edu.medvedew.internship.exceptions.checked.gitlab;

/**
 * Выбрасывается когда происходит ошибка в gitlab клиента
 */
public class GitlabClientException extends Exception {
    public GitlabClientException(String message) {
        super(message);
    }

    public GitlabClientException(Throwable cause) {
        super(cause);
    }
}
