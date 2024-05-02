package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.UserTaskDto;
import ru.ds.edu.medvedew.internship.models.UserTask;
import ru.ds.edu.medvedew.internship.services.UserTaskService;
import ru.ds.edu.medvedew.internship.utils.mappers.UserTaskMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user-tasks")
@Api(tags = "Результаты проверок задач")
@RequiredArgsConstructor
public class UserTaskController {
    private final UserTaskService userTaskService;
    private final UserTaskMapper MAPPER = UserTaskMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить результат проверки по всем задачам")
    public List<UserTaskDto> getAll() {
        return userTaskService.getAll().stream()
                .map(MAPPER::toUserTaskDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать новый отчёт по задаче")
    public ResponseEntity<UserTaskDto> create(@RequestBody UserTaskDto userTaskDto) {
        UserTask created = userTaskService.save(MAPPER.toUserTask(userTaskDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MAPPER.toUserTaskDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить результат проверки задачи по id")
    public UserTaskDto getById(@PathVariable int id) {
        return MAPPER.toUserTaskDto(userTaskService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию по проверке задачи с id")
    public UserTaskDto update(@PathVariable int id, @RequestBody UserTaskDto userTaskDto) {
        UserTask updated = userTaskService.update(id, MAPPER.toUserTask(userTaskDto));
        return MAPPER.toUserTaskDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о проверке")
    public void delete(@PathVariable int id) {
        userTaskService.delete(id);
    }
}
