package ru.ds.edu.medvedew.internship.dto.gitlab;

import lombok.Data;

/**
 * Dto для gitlab пользователя
 */
@Data
public class GitlabUserDto {
    private Integer id;
    private String name;
    private String email;
    private String username;
}
