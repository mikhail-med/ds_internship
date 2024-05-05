package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("Роль пользователя")
@Data
public class RoleDto {
    private int id;
    private String name;
}
