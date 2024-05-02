package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.Internship;

@ApiModel("Занятие")
@Data
public class LessonDto {
    private int id;
    private String name;
    @ApiModelProperty("Id стажировки занятия")
    private int internshipId;
}
