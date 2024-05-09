package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.dto.LessonWithTasksDto;
import ru.ds.edu.medvedew.internship.dto.TaskStatusDto;
import ru.ds.edu.medvedew.internship.dto.UserTaskProgressDto;
import ru.ds.edu.medvedew.internship.dto.UserWithTasksDto;
import ru.ds.edu.medvedew.internship.exceptions.UserNotInternshipParticipant;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserTask;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.services.ProgressService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    private final InternshipService internshipService;
    private final UserService userService;

    @Override
    public List<LessonWithTasksDto> getInternshipProgressForUser(int internshipId, int userId) {
        checkUserInternshipParticipant(internshipId, userId);
        List<LessonWithTasksDto> lessonWithTasksDtos = new ArrayList<>();
        List<Lesson> internshipLessons = internshipService.getAllLessons(internshipId);
        User user = userService.getById(userId);
        // решения которые уже предоставлял пользователь
        Set<UserTask> userSolutions = user.getUserTasks();

        for (Lesson lesson : internshipLessons) {
            lessonWithTasksDtos.add(toLessonWithTaskDto(lesson, userId, userSolutions));
        }

        return lessonWithTasksDtos;
    }

    private LessonWithTasksDto toLessonWithTaskDto(Lesson lesson, int userId, Set<UserTask> userSolutions) {
        List<UserTaskProgressDto> userTaskProgressDtos = new ArrayList<>();
        Set<Task> lessonTasks = lesson.getTask();

        for (Task task : lessonTasks) {
            Optional<UserTask> solution = findLastUserSolutionForTask(userSolutions, task.getId());
            TaskStatus taskStatus;

            if (solution.isPresent()) {
                UserTask userTask = solution.get();
                taskStatus = userTask.getStatus();
            } else {
                taskStatus = TaskStatus.UNCHECKED;
            }
            userTaskProgressDtos.add(new UserTaskProgressDto(userId, task.getId(), taskStatus));
        }

        return new LessonWithTasksDto(lesson.getId(), lesson.getName(),
                lesson.getInternship().getId(),
                userTaskProgressDtos);
    }

    @Override
    public List<UserWithTasksDto> getInternshipProgress(int internshipId) {
        List<UserWithTasksDto> userWithTasksDtos = new ArrayList<>();
        List<User> internshipParticipants = internshipService.getAllParticipants(internshipId);
        List<Task> internshipTasks = getInternshipTasks(internshipId);

        for (User participant : internshipParticipants) {
            Set<UserTask> userSolutions = participant.getUserTasks();
            List<TaskStatusDto> userTasksWithStatus = getUserTaskWithStatusDtos(userSolutions, internshipTasks);
            userWithTasksDtos.add(new UserWithTasksDto(participant.getId(), userTasksWithStatus));
        }

        return userWithTasksDtos;
    }

    /**
     * @param internshipId - id стажировки
     * @return задачи со всех занятий стажировки
     */
    private List<Task> getInternshipTasks(int internshipId) {
        List<Lesson> internshipLessons = internshipService.getAllLessons(internshipId);
        return internshipLessons.stream()
                .flatMap(lesson -> lesson.getTask().stream())
                .collect(Collectors.toList());
    }

    private List<TaskStatusDto> getUserTaskWithStatusDtos(Set<UserTask> userSolutions, List<Task> internshipTasks) {
        List<TaskStatusDto> userTasks = new ArrayList<>();
        for (Task internshipTask : internshipTasks) {
            Optional<UserTask> userSolution = findLastUserSolutionForTask(userSolutions, internshipTask.getId());
            TaskStatus taskStatus;

            if (userSolution.isPresent()) {
                taskStatus = userSolution.get().getStatus();
            } else {
                taskStatus = TaskStatus.UNCHECKED;
            }

            userTasks.add(new TaskStatusDto(internshipTask.getId(), taskStatus));
        }
        return userTasks;
    }

    /**
     * Поиск последнего решения пользвателя для задачи
     *
     * @param userSolutions - результаты проверки задач пользователя
     * @param taskId        - id задачи
     * @return результат проверки решения задачи с последней датой коммита
     */
    private Optional<UserTask> findLastUserSolutionForTask(Set<UserTask> userSolutions, int taskId) {
        return userSolutions.stream().filter(userTask ->
                        userTask.getTask().getId() == taskId)
                .max((ut1, ut2) -> ut1.getCommitCreatedAt().after(ut2.getCommitCreatedAt()) ? 1 : -1);
    }

    private void checkUserInternshipParticipant(int internshipId, int userId) {
        if (!internshipService.isUserActiveInternshipParticipant(internshipId, userId)) {
            throw new UserNotInternshipParticipant(
                    String.format("user with id %d isn't participant of internship with id %d",
                            userId,
                            internshipId)
            );
        }
    }
}
