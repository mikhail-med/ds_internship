package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.UserInternshipDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserInternship;

@Mapper
public interface UserInternshipMapper {
    UserInternshipMapper MAPPER = Mappers.getMapper(UserInternshipMapper.class);

    @Mapping(target = "userId", expression = "java(userInternship.getUser().getId())")
    @Mapping(target = "internshipId", expression = "java(userInternship.getInternship().getId())")
    UserInternshipDto toUserInternshipDto(UserInternship userInternship);

    @Mapping(source = "userId", target = "user", qualifiedByName = "userObject")
    @Mapping(source = "internshipId", target = "internship", qualifiedByName = "internshipObject")
    UserInternship toUserInternship(UserInternshipDto userInternshipDto);

    @Named("userObject")
    default User toUserObject(int userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("internshipObject")
    default Internship toInternshipObject(int internshipId) {
        Internship internship = new Internship();
        internship.setId(internshipId);
        return internship;
    }
}
