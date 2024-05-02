package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.LessonDto;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LessonMapperTest {
    private LessonMapper lessonMapper = LessonMapper.MAPPER;

    @Test
    public void toLesson() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1);
        lessonDto.setInternshipId(2);

        Lesson lesson = lessonMapper.toLesson(lessonDto);

        assertEquals(1, lesson.getId());
        assertEquals(2, lesson.getInternship().getId());
    }

    @Test
    public void toLessonDto() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        Internship internship = new Internship();
        internship.setId(2);
        lesson.setInternship(internship);

        LessonDto lessonDto = lessonMapper.toLessonDto(lesson);

        assertEquals(1, lessonDto.getId());
        assertEquals(2, lessonDto.getInternshipId());
    }

}