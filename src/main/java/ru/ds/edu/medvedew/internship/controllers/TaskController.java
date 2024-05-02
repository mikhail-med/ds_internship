package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.TaskDto;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.services.TaskService;
import ru.ds.edu.medvedew.internship.utils.mappers.TaskMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/tasks")
@Api(tags = "Задачи")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper TASK_MAPPER = TaskMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить все задачи")
    public List<TaskDto> getAll() {
        return taskService.getAll().stream()
                .map(TASK_MAPPER::toTaskDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать новую задачу")
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto taskDto) {
        Task created = taskService.save(TASK_MAPPER.toTask(taskDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TASK_MAPPER.toTaskDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить задачу по id")
    public TaskDto getById(@PathVariable int id) {
        return TASK_MAPPER.toTaskDto(taskService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию по задаче с id")
    public TaskDto update(@PathVariable int id, @RequestBody TaskDto taskDto) {
        Task updated = taskService.update(id, TASK_MAPPER.toTask(taskDto));
        return TASK_MAPPER.toTaskDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о задаче")
    public void delete(@PathVariable int id) {
        taskService.delete(id);
    }
}
