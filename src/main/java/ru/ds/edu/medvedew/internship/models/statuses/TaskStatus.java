package ru.ds.edu.medvedew.internship.models.statuses;

/**
 * Возможные состояния задачи
 */
public enum TaskStatus {
    /**
     * Принята
     */
    PASSED,
    /**
     * Не принята
     */
    FAILED,
    /**
     * Не отправлен (или не проверен)
     */
    UNCHECKED
}
