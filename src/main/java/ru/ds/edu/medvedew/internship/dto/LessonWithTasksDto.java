package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel("Занятие с решениями задачи")
@Data
@AllArgsConstructor
public class LessonWithTasksDto {
    private int id;
    private String name;
    @ApiModelProperty("Id стажировки занятия")
    private int internshipId;
    private List<UserTaskProgressDto> userTasks;
}
