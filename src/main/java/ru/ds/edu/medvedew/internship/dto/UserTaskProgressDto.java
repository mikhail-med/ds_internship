package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;

@ApiModel("Статус пользователя по задаче без лишних для успеваемости полей")
@Data
@AllArgsConstructor
public class UserTaskProgressDto {
    private int userId;
    private int taskId;
    private TaskStatus status;
}
