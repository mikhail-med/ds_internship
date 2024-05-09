package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.dto.LessonWithTasksDto;
import ru.ds.edu.medvedew.internship.dto.TaskStatusDto;
import ru.ds.edu.medvedew.internship.dto.UserTaskProgressDto;
import ru.ds.edu.medvedew.internship.dto.UserWithTasksDto;
import ru.ds.edu.medvedew.internship.exceptions.ResourceCantBeUpdated;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.*;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.services.UserInternshipService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final UserService userService;
    private final UserInternshipService userInternshipService;

    @Override
    public List<Internship> getAll() {
        return internshipRepository.findAll();
    }

    @Override
    public Internship getById(int id) {
        return internshipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Internship with id %d not found", id)));
    }

    @Transactional
    @Override
    public Internship save(Internship internship) {
        return internshipRepository.save(internship);
    }

    @Transactional
    @Override
    public Internship update(int id, Internship internship) {
        if (!internshipRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Internship with id %d doesn't exist", id));
        }
        internship.setId(id);
        return internshipRepository.save(internship);
    }

    @Transactional
    @Override
    public void delete(int id) {
        internshipRepository.deleteById(id);
    }

    /**
     * Получить участников стажировки
     *
     * @param internshipId id стажировки
     * @return список участников
     */
    @Override
    public List<User> getAllParticipants(int internshipId) {
        Internship internship = getById(internshipId);
        return new ArrayList<>(internship.getParticipants());
    }

    /**
     * Добавить пользователя на стажировку
     *
     * @param userId       - id пользователя
     * @param internshipId - id стажировки
     */
    @Transactional
    @Override
    public void addUserToInternship(int userId, int internshipId) {
        Internship internship = getById(internshipId);
        checkApplicationsDateNotExpired(internship.getApplicationsDeadline(), internshipId);
        Set<User> participants = internship.getParticipants();

        // проверка что пользователя с таким id ещё нет на стажировке
        if (participants.stream().noneMatch(p -> p.getId() == userId)) {
            User user = userService.getById(userId);
            userInternshipService.save(
                    new UserInternship(user, internship, UserInternshipStatus.SUBMITTED_APPLICATION)
            );
        } else {
            throw new ResourceCantBeUpdated(String.format(
                    "User with id %d is already participating in internship with id %d",
                    userId, internshipId)
            );
        }
    }

    /**
     * Получить все занятия со стажировки
     *
     * @param internshipId - id стажировки
     * @return список занятий
     */
    @Override
    public List<Lesson> getAllLessons(int internshipId) {
        Internship internship = getById(internshipId);
        return new ArrayList<>(internship.getLessons());
    }

    /**
     * проверка того, что срок подачи заявок не истёк
     *
     * @param applicationsDate - дата и время окончания подачи заявок на стажировку
     * @param internshipId     - id стажировки
     */
    private void checkApplicationsDateNotExpired(Date applicationsDate, int internshipId) {
        if (applicationsDate.before(new Date())) {
            throw new ResourceCantBeUpdated(String.format(
                    "The application period for internship with id %d has already expired",
                    internshipId)
            );
        }
    }

    @Override
    public List<LessonWithTasksDto> getInternshipProgressForUser(int internshipId, int userId) {
        List<LessonWithTasksDto> lessonWithTasksDtos = new ArrayList<>();
        List<Lesson> internshipLessons = getAllLessons(internshipId);
        User user = userService.getById(userId);
        // решения которые уже предоставлял пользователь
        Set<UserTask> userSolutions = user.getUserTasks();

        for (Lesson lesson : internshipLessons) {
            lessonWithTasksDtos.add(toLessonWithTaskDto(lesson, userId, userSolutions));
        }

        return lessonWithTasksDtos;
    }

    @Override
    public List<UserWithTasksDto> getInternshipProgress(int internshipId) {
        List<UserWithTasksDto> userWithTasksDtos = new ArrayList<>();
        List<User> internshipParticipants = getAllParticipants(internshipId);
        List<Task> internshipTasks = getInternshipTasks(internshipId);

        for (User participant : internshipParticipants) {
            UserWithTasksDto userWithTasksDto = mapToUserWithTasksDto(participant);
            Set<UserTask> userSolutions = participant.getUserTasks();
            List<TaskStatusDto> userTasksWithStatus = getUserTaskWithStatusDtos(userSolutions, internshipTasks);
            userWithTasksDto.setTasks(userTasksWithStatus);
            userWithTasksDtos.add(userWithTasksDto);
        }

        return userWithTasksDtos;
    }

    private List<Task> getInternshipTasks(int internshipId) {
        List<Lesson> internshipLessons = getAllLessons(internshipId);
        return internshipLessons.stream()
                .flatMap(lesson -> lesson.getTask().stream())
                .collect(Collectors.toList());
    }

    private List<TaskStatusDto> getUserTaskWithStatusDtos(Set<UserTask> userSolutions, List<Task> internshipTasks) {
        List<TaskStatusDto> userTasks = new ArrayList<>();
        for (Task internshipTask : internshipTasks) {
            TaskStatusDto taskStatusDto = mapToTaskStatusDto(internshipTask);

            Optional<UserTask> userSolution = findLastUserSolutionForTask(userSolutions, internshipTask.getId());
            if (userSolution.isPresent()) {
                taskStatusDto.setTaskStatus(userSolution.get().getStatus());
            } else {
                taskStatusDto.setTaskStatus(TaskStatus.UNCHECKED);
            }

            userTasks.add(taskStatusDto);
        }
        return userTasks;
    }

    private LessonWithTasksDto toLessonWithTaskDto(Lesson lesson, int userId, Set<UserTask> userSolutions) {
        LessonWithTasksDto lessonWithTasksDto = mapToLessonWithTasksDto(lesson);
        List<UserTaskProgressDto> userTaskProgressDtos = new ArrayList<>();
        Set<Task> lessonTasks = lesson.getTask();

        for (Task task : lessonTasks) {
            Optional<UserTask> solution = findLastUserSolutionForTask(userSolutions, task.getId());

            if (solution.isPresent()) {
                UserTask userTask = solution.get();
                userTaskProgressDtos.add(toUserTaskProgressDto(userTask.getUser().getId(),
                        userTask.getTask().getId(),
                        userTask.getStatus()));
            } else {
                userTaskProgressDtos.add(toUserTaskProgressDto(userId, task.getId(), TaskStatus.UNCHECKED));
            }
        }
        lessonWithTasksDto.setUserTasks(userTaskProgressDtos);
        return lessonWithTasksDto;
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

    private LessonWithTasksDto mapToLessonWithTasksDto(Lesson lesson) {
        LessonWithTasksDto lessonWithTasksDto = new LessonWithTasksDto();
        lessonWithTasksDto.setId(lesson.getId());
        lessonWithTasksDto.setName(lesson.getName());
        lessonWithTasksDto.setInternshipId(lesson.getInternship().getId());
        return lessonWithTasksDto;
    }

    private UserTaskProgressDto toUserTaskProgressDto(int userId, int taskId, TaskStatus taskStatus) {
        UserTaskProgressDto userTaskProgressDto = new UserTaskProgressDto();
        userTaskProgressDto.setUserId(userId);
        userTaskProgressDto.setTaskId(taskId);
        userTaskProgressDto.setStatus(taskStatus);
        return userTaskProgressDto;
    }

    private UserWithTasksDto mapToUserWithTasksDto(User user) {
        UserWithTasksDto userWithTasksDto = new UserWithTasksDto();
        userWithTasksDto.setUserId(user.getId());
        return userWithTasksDto;
    }

    private TaskStatusDto mapToTaskStatusDto(Task task) {
        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setTaskId(task.getId());
        return taskStatusDto;
    }

}
