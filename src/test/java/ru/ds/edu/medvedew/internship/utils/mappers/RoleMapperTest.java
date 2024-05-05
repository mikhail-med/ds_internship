package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.RoleDto;
import ru.ds.edu.medvedew.internship.models.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleMapperTest {
    private RoleMapper roleMapper = RoleMapper.MAPPER;

    @Test
    void toRoleDto() {
        Role role = new Role();
        role.setId(1);
        role.setName("name");

        RoleDto roleDto = roleMapper.toRoleDto(role);

        assertEquals(1, roleDto.getId());
        assertEquals("name", roleDto.getName());
    }

    @Test
    void toRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setName("name");

        Role role = roleMapper.toRole(roleDto);

        assertEquals(1, role.getId());
        assertEquals("name", role.getName());
    }
}