package ru.ds.edu.medvedew.internship.dto.gitlab;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto для создания gitlab пользователя
 */
@Data
@AllArgsConstructor
public class GitlabUserCreateDto {
    private String name;
    private String email;
    private String username;
    private String password;
}
