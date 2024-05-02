package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.TaskDto;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {
    private TaskMapper taskMapper = TaskMapper.MAPPER;

    @Test
    void toTaskDto() {
        Task task = new Task();
        task.setId(1);
        Lesson lesson = new Lesson();
        lesson.setId(2);
        task.setLesson(lesson);

        TaskDto taskDto = taskMapper.toTaskDto(task);

        assertEquals(1, taskDto.getId());
        assertEquals(2, taskDto.getLessonId());
    }

    @Test
    void toTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1);
        taskDto.setLessonId(2);

        Task task = taskMapper.toTask(taskDto);

        assertEquals(1, task.getId());
        assertEquals(2, task.getLesson().getId());
    }
}