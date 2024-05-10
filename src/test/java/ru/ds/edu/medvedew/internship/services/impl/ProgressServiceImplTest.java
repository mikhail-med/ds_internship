package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.dto.LessonWithTasksDto;
import ru.ds.edu.medvedew.internship.dto.UserWithTasksDto;
import ru.ds.edu.medvedew.internship.models.*;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProgressServiceImplTest {
    @Mock
    private InternshipService internshipService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProgressServiceImpl progressServiceImpl;

    @Test
    void getInternshipProgressForUser() {
        User user = mock(User.class);
        Task task = new Task();
        task.setId(1);
        UserTask userTask = new UserTask();
        userTask.setId(1);
        userTask.setUser(user);
        userTask.setTask(task);
        userTask.setStatus(TaskStatus.PASSED);
        Internship internship = new Internship();
        internship.setId(1);
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setTask(Set.of(task));
        lesson.setInternship(internship);
        doReturn(user).when(userService).getById(1);
        doReturn(true).when(internshipService).isUserActiveInternshipParticipant(1, 1);
        doReturn(List.of(lesson)).when(internshipService).getAllLessons(1);
        doReturn(Set.of(userTask)).when(user).getUserTasks();

        List<LessonWithTasksDto> lessonWithTasksDtos = progressServiceImpl
                .getInternshipProgressForUser(1, 1);


        assertNotNull(lessonWithTasksDtos);
        assertEquals(1, lessonWithTasksDtos.size());
        assertEquals(1, lessonWithTasksDtos.get(0).getUserTasks().size());
        assertEquals(1, lessonWithTasksDtos.get(0).getUserTasks().get(0).getUserId());
        assertEquals(1, lessonWithTasksDtos.get(0).getUserTasks().get(0).getTaskId());
    }

    @Test
    void getInternshipProgress() {
        User user = mock(User.class);
        Task task = new Task();
        task.setId(1);
        UserTask userTask = new UserTask();
        userTask.setId(1);
        userTask.setUser(user);
        userTask.setTask(task);
        userTask.setStatus(TaskStatus.PASSED);
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setTask(Set.of(task));
        doReturn(List.of(lesson)).when(internshipService).getAllLessons(1);
        doReturn(List.of(user)).when(internshipService).getAllParticipants(1);
        doReturn(Set.of(userTask)).when(user).getUserTasks();
        doReturn(1).when(user).getId();

        List<UserWithTasksDto> userWithTasksDtos = progressServiceImpl.getInternshipProgress(1);

        assertNotNull(userWithTasksDtos);
        assertEquals(1, userWithTasksDtos.size());
        assertEquals(1, userWithTasksDtos.get(0).getUserId());
        assertEquals(1, userWithTasksDtos.get(0).getTasks().get(0).getTaskId());
    }
}