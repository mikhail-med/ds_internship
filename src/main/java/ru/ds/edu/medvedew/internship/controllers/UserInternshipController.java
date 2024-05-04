package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.UserInternshipDto;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.services.UserInternshipService;
import ru.ds.edu.medvedew.internship.utils.mappers.UserInternshipMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Эндпойнты для подачи заявок на стажировку для пользователей реализованы
 * в других контроллерах,
 * этот контроллер предоставляет бОльшую свободу в изменении данных
 * и должен быть доступен только админам.
 */
@RestController
@RequestMapping("/v1/user-internships")
@Api(tags = "Стажировки пользователей")
@RequiredArgsConstructor
public class UserInternshipController {
    private final UserInternshipService userInternshipService;
    private final UserInternshipMapper MAPPER = UserInternshipMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить все стажировки всех пользователей")
    public List<UserInternshipDto> getAll() {
        return userInternshipService.getAll().stream()
                .map(MAPPER::toUserInternshipDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Добавить пользоавтелю стажировку с некоторым статусом")
    public ResponseEntity<UserInternshipDto> create(@RequestBody UserInternshipDto userInternshipDto) {
        UserInternship created = userInternshipService.save(MAPPER.toUserInternship(userInternshipDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MAPPER.toUserInternshipDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить запись о стажировке пользователя со статусом пользователя")
    public UserInternshipDto getById(@PathVariable int id) {
        return MAPPER.toUserInternshipDto(userInternshipService.getById(id));
    }

    /**
     * обращаясь к этому методу можно обновить статус пользователя на стажировке
     * (принять заявку, исключить и т.д.)
     */
    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию о стажировке пользователя")
    public UserInternshipDto update(@PathVariable int id, @RequestBody UserInternshipDto userInternshipDto) {
        UserInternship updated = userInternshipService.update(id, MAPPER.toUserInternship(userInternshipDto));
        return MAPPER.toUserInternshipDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о стажировке пользователя")
    public void delete(@PathVariable int id) {
        userInternshipService.delete(id);
    }

    @GetMapping("/status/{status}")
    @ApiOperation("Получить записи о стажировках пользователей со статусом")
    public List<UserInternshipDto> getAllWithStatus(@ApiParam("SUBMITTED_APPLICATION, INTERNSHIP_PARTICIPANT, EXPELLED, COMPLETED")
                                                    @PathVariable String status) {
        return userInternshipService.getAllWithStatus(UserInternshipStatus.valueOf(status.toUpperCase())).stream()
                .map(MAPPER::toUserInternshipDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/status/{status}")
    @ApiOperation("Получить записи о стажировке с id и пользователях со статусом")
    public List<UserInternshipDto> getAllWithStatusAndStatus(@PathVariable int id,
                                                             @ApiParam("SUBMITTED_APPLICATION, INTERNSHIP_PARTICIPANT, EXPELLED, COMPLETED")
                                                             @PathVariable String status) {
        return userInternshipService.getAllWithStatusOnInternship(UserInternshipStatus.valueOf(status.toUpperCase()), id)
                .stream()
                .map(MAPPER::toUserInternshipDto)
                .collect(Collectors.toList());
    }
}
