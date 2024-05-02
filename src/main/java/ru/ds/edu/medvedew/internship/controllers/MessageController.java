package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.MessageDto;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.services.MessageService;
import ru.ds.edu.medvedew.internship.utils.mappers.MessageMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/messages")
@Api(tags = "Сообщения")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper MESSAGE_MAPPER = MessageMapper.MAPPER;


    @GetMapping
    @ApiOperation("Получить все сообщения")
    public List<MessageDto> getAll() {
        return messageService.getAll().stream()
                .map(MESSAGE_MAPPER::toMessageDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создать новое сообщение")
    public ResponseEntity<MessageDto> create(@RequestBody MessageDto messageDto) {
        Message created = messageService.save(MESSAGE_MAPPER.toMessage(messageDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MESSAGE_MAPPER.toMessageDto(created));
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить сообщение по id")
    public MessageDto getById(@PathVariable int id) {
        return MESSAGE_MAPPER.toMessageDto(messageService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию сообщения с id")
    public MessageDto update(@PathVariable int id, @RequestBody MessageDto messageDto) {
        Message updated = messageService.update(id, MESSAGE_MAPPER.toMessage(messageDto));
        return MESSAGE_MAPPER.toMessageDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Удалить сообщение c id")
    public void delete(@PathVariable int id) {
        messageService.delete(id);
    }

}
