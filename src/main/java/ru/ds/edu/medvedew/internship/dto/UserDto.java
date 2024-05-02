package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import ru.ds.edu.medvedew.internship.models.Education;
import ru.ds.edu.medvedew.internship.models.UserContacts;

import java.util.Date;

@ApiModel("Пользователь")
@Data
public class UserDto {
    private int id;
    private String name;
    private UserContacts contacts;
    private String username;
    private String information;
    private Date dateOfBirth;
    private String city;
    private Education education;
    private String password;
}
