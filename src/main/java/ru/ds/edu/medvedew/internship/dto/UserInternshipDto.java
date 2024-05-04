package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;

@ApiModel("Статус пользователя на стажировке")
@Data
public class UserInternshipDto {
    private int id;
    private int userId;
    private int internshipId;
    private UserInternshipStatus status;
}
