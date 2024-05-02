package ru.ds.edu.medvedew.internship.exceptions;

/**
 * Выбрасывается если нет записи в бд (например нет записи с некоторым id)
 */
public class ResourceNotFoundException extends IllegalArgumentException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
