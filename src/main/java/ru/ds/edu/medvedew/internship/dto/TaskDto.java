package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Задача занятия")
@Data
public class TaskDto {
    private int id;
    private String name;
    private String repository;
    @ApiModelProperty("Id занятия к которому относится задача")
    private int lessonId;
}
