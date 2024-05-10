package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.UserDto;
import ru.ds.edu.medvedew.internship.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private UserMapper userMapper = UserMapper.MAPPER;

    @Test
    void toUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("name");
        userDto.setCity("city");

        User user = userMapper.toUser(userDto);

        assertEquals(1, user.getId());
        assertEquals("name", user.getName());
        assertEquals("city", user.getCity());
    }

    @Test
    void toUserDto() {
        User user = new User();
        user.setId(1);
        user.setName("name");
        user.setCity("city");

        UserDto userDto = userMapper.toUserDto(user);

        assertEquals(1, userDto.getId());
        assertEquals("name", userDto.getName());
        assertEquals("city", userDto.getCity());
    }
}