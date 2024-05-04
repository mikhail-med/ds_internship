package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.InternshipDto;
import ru.ds.edu.medvedew.internship.dto.LessonDto;
import ru.ds.edu.medvedew.internship.dto.UserSmallDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.utils.mappers.InternshipMapper;
import ru.ds.edu.medvedew.internship.utils.mappers.LessonMapper;
import ru.ds.edu.medvedew.internship.utils.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/internships")
@Api(tags = "Стажировки")
@RequiredArgsConstructor
public class InternshipController {
    private final InternshipService internshipService;
    private final InternshipMapper INTERNSHIP_MAPPER = InternshipMapper.MAPPER;
    private final UserMapper USER_MAPPER = UserMapper.MAPPER;
    private final LessonMapper LESSON_MAPPER = LessonMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить все стажировки")
    public List<InternshipDto> getAll() {
        return internshipService.getAll().stream()
                .map(INTERNSHIP_MAPPER::toInternshipDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать новую стажировку")
    public ResponseEntity<InternshipDto> create(@RequestBody InternshipDto internshipDto) {
        Internship created = internshipService.save(INTERNSHIP_MAPPER.toInternship(internshipDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(INTERNSHIP_MAPPER.toInternshipDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить стажировку по id")
    public InternshipDto getById(@PathVariable int id) {
        return INTERNSHIP_MAPPER.toInternshipDto(internshipService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию по стажировке с id")
    public InternshipDto update(@PathVariable int id, @RequestBody InternshipDto internshipDto) {
        Internship updated = internshipService.update(id, INTERNSHIP_MAPPER.toInternship(internshipDto));
        return INTERNSHIP_MAPPER.toInternshipDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о стажировке")
    public void delete(@PathVariable int id) {
        internshipService.delete(id);
    }

    @GetMapping("/{id}/users")
    @ApiOperation("Получить участников стажировки по id стажировки")
    public List<UserSmallDto> getAllParticipants(@PathVariable int id) {
        return USER_MAPPER.toUserSmallDtoList(internshipService.getAllParticipants(id));
    }

    @PostMapping("/{internshipId}/user/{userId}")
    @ApiOperation("Записать пользователя на стажировку по id стажировки")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserToInternship(@PathVariable("internshipId") int internshipId,
                                    @PathVariable("userId") int userId) {
        internshipService.addUserToInternship(userId, internshipId);
    }

    @GetMapping("/{id}/lessons")
    @ApiOperation("Получить занятия некоторой стажировки по id стажировки")
    public List<LessonDto> getAllLessons(@PathVariable int id) {
        return internshipService.getAllLessons(id).stream()
                .map(LESSON_MAPPER::toLessonDto)
                .collect(Collectors.toList());
    }
}
