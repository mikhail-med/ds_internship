package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.LessonDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;

/**
 * Mapper для занятия и dto
 */
@Mapper
public interface LessonMapper {
    LessonMapper MAPPER = Mappers.getMapper(LessonMapper.class);

    @Mapping(target = "internshipId", expression = "java(lesson.getInternship().getId())")
    LessonDto toLessonDto(Lesson lesson);

    @Mapping(source = "internshipId", target = "internship", qualifiedByName = "internshipObject")
    Lesson toLesson(LessonDto lessonDto);

    @Named("internshipObject")
    default Internship toInternshipObject(int internshipId) {
        Internship internship = new Internship();
        internship.setId(internshipId);
        return internship;
    }
}
