package ru.ds.edu.medvedew.internship.models.statuses;

/**
 * Возможные состояния пользователя
 */
public enum UserStatus {
    /**
     * Отправил заявку
     */
    SUBMITTED_APPLICATION,
    /**
     * Учавствует в стажировке
     */
    INTERNSHIP_PARTICIPANT,
    /**
     * Отчислен
     */
    EXPELLED,
    /**
     * Завершил стажировку
     */
    COMPLETED
}
