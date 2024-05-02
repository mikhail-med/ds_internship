package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@ApiModel("Исключение")
@Data
public class ExceptionDto {
    private final HttpStatus httpStatus;
    private final String message;
    private final Date timestamp;
}
