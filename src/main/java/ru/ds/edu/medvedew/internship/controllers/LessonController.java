package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.LessonDto;
import ru.ds.edu.medvedew.internship.dto.TaskDto;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.services.LessonService;
import ru.ds.edu.medvedew.internship.utils.mappers.LessonMapper;
import ru.ds.edu.medvedew.internship.utils.mappers.TaskMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/lessons")
@Api(tags = "Занятия")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper LESSON_MAPPER = LessonMapper.MAPPER;
    private final TaskMapper TASK_MAPPER = TaskMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить все занятия")
    public List<LessonDto> getAll() {
        return lessonService.getAll().stream()
                .map(LESSON_MAPPER::toLessonDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать новое занятие")
    public ResponseEntity<LessonDto> create(@RequestBody LessonDto lessonDto) {
        Lesson created = lessonService.save(LESSON_MAPPER.toLesson(lessonDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LESSON_MAPPER.toLessonDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить занятие по id")
    public LessonDto getById(@PathVariable int id) {
        return LESSON_MAPPER.toLessonDto(lessonService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию по занятию с id")
    public LessonDto update(@PathVariable int id, @RequestBody LessonDto lessonDto) {
        Lesson updated = lessonService.update(id, LESSON_MAPPER.toLesson(lessonDto));
        return LESSON_MAPPER.toLessonDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о занятии")
    public void delete(@PathVariable int id) {
        lessonService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @ApiOperation("Получить все задачи занятия")
    public List<TaskDto> getAllTasksForLesson(@PathVariable int id) {
        return lessonService.getAllTasksForLesson(id).stream()
                .map(TASK_MAPPER::toTaskDto)
                .collect(Collectors.toList());
    }
}
