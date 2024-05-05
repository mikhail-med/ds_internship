package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.MessageDto;
import ru.ds.edu.medvedew.internship.dto.UserDto;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.services.UserService;
import ru.ds.edu.medvedew.internship.utils.mappers.MessageMapper;
import ru.ds.edu.medvedew.internship.utils.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@Api(tags = "Пользователи")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper USER_MAPPER = UserMapper.MAPPER;
    private final MessageMapper MESSAGE_MAPPER = MessageMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить всех пользователей")
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(USER_MAPPER::toUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать нового пользователя")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User created = userService.save(USER_MAPPER.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(USER_MAPPER.toUserDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить пользователя по id")
    public UserDto getById(@PathVariable int id) {
        return USER_MAPPER.toUserDto(userService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию по пользователю с id")
    public UserDto update(@PathVariable int id, @RequestBody UserDto userDto) {
        User updated = userService.update(id, USER_MAPPER.toUser(userDto));
        return USER_MAPPER.toUserDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить иноформацию о пользователе")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/messages")
    @ApiOperation("Получить сообщения пользователя (отправленные и полученные)")
    public List<MessageDto> getAllUserMessages(@PathVariable int id) {
        return userService.getAllMessagesForUser(id).stream()
                .map(MESSAGE_MAPPER::toMessageDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/sent-messages")
    @ApiOperation("Получить сообщения отправленные пользователем")
    public List<MessageDto> getAllUserSentMessages(@PathVariable int id) {
        return userService.getAllMessagesSentByUser(id).stream()
                .map(MESSAGE_MAPPER::toMessageDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/received-messages")
    @ApiOperation("Получить сообщения полученные пользователем")
    public List<MessageDto> getAllUserReceivedMessages(@PathVariable int id) {
        return userService.getAllMessageReceivedByUser(id).stream()
                .map(MESSAGE_MAPPER::toMessageDto)
                .collect(Collectors.toList());
    }

}
