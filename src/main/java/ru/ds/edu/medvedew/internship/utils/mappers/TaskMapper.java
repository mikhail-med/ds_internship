package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.TaskDto;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Task;

/**
 * Mapper для задачи и dto
 */
@Mapper
public interface TaskMapper {
    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "lessonId", expression = "java(task.getLesson().getId())")
    TaskDto toTaskDto(Task task);

    @Mapping(source = "lessonId", target = "lesson", qualifiedByName = "lessonObject")
    Task toTask(TaskDto taskDto);

    @Named("lessonObject")
    default Lesson toLessonObject(int lessonId) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        return lesson;
    }
}
