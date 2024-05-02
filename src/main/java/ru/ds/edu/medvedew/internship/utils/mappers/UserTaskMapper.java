package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.UserTaskDto;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserTask;

/**
 * Mapper для результата решения задачи и dto
 */
@Mapper
public interface UserTaskMapper {
    UserTaskMapper MAPPER = Mappers.getMapper(UserTaskMapper.class);

    @Mapping(target = "userId", expression = "java(userTask.getUser().getId())")
    @Mapping(target = "taskId", expression = "java(userTask.getTask().getId())")
    UserTaskDto toUserTaskDto(UserTask userTask);

    @Mapping(source = "userId", target = "user", qualifiedByName = "userObject")
    @Mapping(source = "taskId", target = "task", qualifiedByName = "taskObject")
    UserTask toUserTask(UserTaskDto userTaskDto);

    @Named("userObject")
    default User toUserObject(int userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("taskObject")
    default Task toTaskObject(int taskId) {
        Task task = new Task();
        task.setId(taskId);
        return task;
    }
}
