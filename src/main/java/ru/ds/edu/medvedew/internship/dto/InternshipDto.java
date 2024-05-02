package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.statuses.InternshipStatus;

import java.util.Date;

@ApiModel("Стажировка")
@Data
public class InternshipDto {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    @ApiModelProperty("Последний день подачи заявки")
    private Date applicationsDeadline;
    private InternshipStatus status;
}
