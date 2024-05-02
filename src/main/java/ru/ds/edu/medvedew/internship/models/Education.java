package ru.ds.edu.medvedew.internship.models;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Информация об образовании пользователя
 */
@Embeddable
@Data
public class Education {
    /**
     * студент, закончил обучение, не имею профильного образования и что угодно ещё
     */
    private String status;
    private String university;
    private String faculty;
    private String speciality;
    private Integer course;
}
