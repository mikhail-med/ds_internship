package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.InternshipDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.utils.mappers.InternshipMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/internships")
@Api(tags = "Стажировки")
@RequiredArgsConstructor
public class InternshipController {
    private final InternshipService internshipService;
    private final InternshipMapper INTERNSHIP_MAPPER = InternshipMapper.MAPPER;

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
}
