package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.RoleDto;
import ru.ds.edu.medvedew.internship.models.Role;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleDto toRoleDto(Role role);

    Role toRole(RoleDto roleDto);
}
