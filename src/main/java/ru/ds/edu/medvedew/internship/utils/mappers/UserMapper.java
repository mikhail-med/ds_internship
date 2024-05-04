package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.UserDto;
import ru.ds.edu.medvedew.internship.dto.UserSmallDto;
import ru.ds.edu.medvedew.internship.models.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper для пользователя и dto
 */
@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

    UserSmallDto toUserSmallDto(User user);

    User toUserDto(UserSmallDto userSmallDto);

    default List<UserSmallDto> toUserSmallDtoList(List<User> users) {
        return users.stream().map(this::toUserSmallDto)
                .collect(Collectors.toList());
    }
}
