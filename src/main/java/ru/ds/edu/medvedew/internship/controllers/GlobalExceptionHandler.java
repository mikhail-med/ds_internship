package ru.ds.edu.medvedew.internship.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ds.edu.medvedew.internship.dto.ExceptionDto;
import ru.ds.edu.medvedew.internship.exceptions.ResourceCantBeUpdated;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> resourceNotFoundExc(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(HttpStatus.NOT_FOUND, e.getMessage(), new Date()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> resourceCantBeUpdated(ResourceCantBeUpdated e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionDto(HttpStatus.CONFLICT, e.getMessage(), new Date()));
    }

}
