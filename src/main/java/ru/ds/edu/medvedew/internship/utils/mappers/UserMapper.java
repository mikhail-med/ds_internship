package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.UserDto;
import ru.ds.edu.medvedew.internship.models.User;

/**
 * Mapper для пользователя и dto
 */
@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
