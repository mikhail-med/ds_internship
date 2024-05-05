package ru.ds.edu.medvedew.internship.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.edu.medvedew.internship.dto.RoleDto;
import ru.ds.edu.medvedew.internship.services.RoleService;
import ru.ds.edu.medvedew.internship.utils.mappers.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/roles")
@Api(tags = "Роли пользователя")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper ROLE_MAPPER = RoleMapper.MAPPER;

    @GetMapping
    @ApiOperation("Получить все роли")
    public List<RoleDto> getAll() {
        return roleService.getAll().stream()
                .map(ROLE_MAPPER::toRoleDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить роль по id")
    public RoleDto getById(@PathVariable int id) {
        return ROLE_MAPPER.toRoleDto(roleService.getById(id));
    }

}
