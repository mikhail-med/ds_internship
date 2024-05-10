package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.UserInternshipDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInternshipMapperTest {
    private UserInternshipMapper userInternshipMapper = UserInternshipMapper.MAPPER;

    @Test
    void toUserInternshipDto() {
        UserInternship userInternship = new UserInternship();
        userInternship.setId(1);
        User user = new User();
        user.setId(2);
        userInternship.setUser(user);
        Internship internship = new Internship();
        internship.setId(3);
        userInternship.setInternship(internship);

        UserInternshipDto userInternshipDto = userInternshipMapper.toUserInternshipDto(userInternship);

        assertEquals(1, userInternshipDto.getId());
        assertEquals(2, userInternshipDto.getUserId());
        assertEquals(3, userInternshipDto.getInternshipId());
    }

    @Test
    void toUserInternship() {
        UserInternshipDto userInternshipDto = new UserInternshipDto();
        userInternshipDto.setId(1);
        userInternshipDto.setUserId(2);
        userInternshipDto.setInternshipId(3);
        userInternshipDto.setStatus(UserInternshipStatus.COMPLETED);

        UserInternship userInternship = userInternshipMapper.toUserInternship(userInternshipDto);

        assertEquals(1, userInternship.getId());
        assertEquals(2, userInternship.getUser().getId());
        assertEquals(3, userInternship.getInternship().getId());
        assertEquals(UserInternshipStatus.COMPLETED, userInternship.getStatus());
    }
}