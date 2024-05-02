package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.UserTaskDto;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTaskMapperTest {
    private UserTaskMapper userTaskMapper = UserTaskMapper.MAPPER;

    @Test
    void toUserTaskDto() {
        UserTask userTask = new UserTask();
        userTask.setId(1);
        User user = new User();
        user.setId(2);
        userTask.setUser(user);
        Task task = new Task();
        task.setId(3);
        userTask.setTask(task);

        UserTaskDto userTaskDto = userTaskMapper.toUserTaskDto(userTask);

        assertEquals(1, userTaskDto.getId());
        assertEquals(2, userTaskDto.getUserId());
        assertEquals(3, userTaskDto.getTaskId());
    }

    @Test
    void toUserTask() {
        UserTaskDto userTaskDto = new UserTaskDto();
        userTaskDto.setId(1);
        userTaskDto.setUserId(2);
        userTaskDto.setTaskId(3);

        UserTask userTask = userTaskMapper.toUserTask(userTaskDto);

        assertEquals(1, userTask.getId());
        assertEquals(2, userTask.getUser().getId());
        assertEquals(3, userTask.getTask().getId());
    }
}