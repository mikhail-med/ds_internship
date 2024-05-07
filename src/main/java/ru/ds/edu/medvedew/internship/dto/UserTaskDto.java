package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;

import java.util.Date;

@ApiModel("Статус задачи по пользователю")
@Data
public class UserTaskDto {
    private int id;
    @ApiModelProperty("Id пользователя предоставившего решение")
    private int userId;
    @ApiModelProperty("Id решённой задачи")
    private int taskId;
    private TaskStatus status;
    private String comment;
    @ApiModelProperty("Время проверки задачи")
    private Date onMoment;
    @ApiModelProperty("Время проверки задачи")
    private Date commitCreatedAt;
}
