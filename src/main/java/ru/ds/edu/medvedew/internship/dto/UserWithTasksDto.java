package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel("Для ведомости по стажировке")
@Data
@AllArgsConstructor
public class UserWithTasksDto {
    private int userId;
    private List<TaskStatusDto> tasks;
}
