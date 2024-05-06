package ru.ds.edu.medvedew.internship.dto.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Dto для gitlab проекта
 */
@Data
public class ProjectDto {
    private int id;
    private String name;
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;
}
