package ru.ds.edu.medvedew.internship.models;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Данные для связи с пользователем
 */
@Embeddable
@Data
public class UserContacts {
    private String email;
    private String phoneNumber;
    private String telegramId;
}
