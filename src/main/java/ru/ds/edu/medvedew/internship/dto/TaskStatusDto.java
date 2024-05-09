package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;

@ApiModel("Задача для ведомости")
@Data
public class TaskStatusDto {
    private int taskId;
    private TaskStatus taskStatus;
}
