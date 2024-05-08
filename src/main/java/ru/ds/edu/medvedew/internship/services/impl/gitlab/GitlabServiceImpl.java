package ru.ds.edu.medvedew.internship.services.impl.gitlab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.dto.UserTaskCommit;
import ru.ds.edu.medvedew.internship.dto.gitlab.GitlabCommit;
import ru.ds.edu.medvedew.internship.exceptions.GitlabUserCantBeCreated;
import ru.ds.edu.medvedew.internship.models.*;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.services.*;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabClient;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GitlabServiceImpl implements GitlabService {
    private final MessageService messageService;
    private final UserService userService;
    private final LessonService lessonService;
    private final RoleService roleService;
    private final GitlabClient gitlabClient;
    private final UserTaskService userTaskService;
    private final UserInternshipService userInternshipService;

    @Transactional
    @Override
    public void publishLesson(int lessonId, String privateToken) {
        Lesson lesson = lessonService.getById(lessonId);
        Internship internship = lesson.getInternship();
        Set<Task> lessonTasks = lesson.getTask();
        List<UserInternship> internshipParticipants = internship.getUsers();
        Set<User> admins = roleService.getByName("ADMIN").getUsers();

        for (UserInternship participant : internshipParticipants) {
            if (participant.getStatus().equals(UserInternshipStatus.INTERNSHIP_PARTICIPANT)) {
                User user = participant.getUser();
                for (Task task : lessonTasks) {
                    boolean isForked = gitlabClient.forkProject(task.getRepository(), user.getUsername(),
                            privateToken);
                    if (!isForked) {
                        writeMessagesToAdminAboutNoForked(admins, task.getName(), user.getId());
                    }
                }
            }
        }
    }

    /**
     * Пароль генерится как UUID и отправляется пользователю сообщением
     */
    @Transactional
    @Override
    public void createGitlabAccountForUser(int userId, String privateToken) {
        User user = userService.getById(userId);
        String password = UUID.randomUUID().toString();
        boolean isUserCreated = gitlabClient.createUser(user.getName(),
                user.getUsername(),
                user.getContacts().getEmail(),
                password,
                privateToken);

        if (isUserCreated) {
            messageService.sendMessageFromUserToUserWithText(1, userId,
                    "Password for your gitlab account: " + password);
        } else {
            log.error("user.id = {}. user's gitlab account isn't created", userId);
            throw new GitlabUserCantBeCreated(String.format("gitlab account for user with id %d cant be created",
                    userId));
        }
    }

    @Override
    public List<UserTaskCommit> getNewUncheckedCommitsForLesson(int lessonId, String privateToken) {
        List<UserTaskCommit> lastCommits = new ArrayList<>();
        Lesson lesson = lessonService.getById(lessonId);
        List<UserInternship> activeUsers = userInternshipService.getAllWithStatusOnInternship(
                UserInternshipStatus.INTERNSHIP_PARTICIPANT,
                lesson.getInternship().getId());
        List<UserTask> solutionForLessonTasks = userTaskService.getAllForLesson(lessonId);
        List<Task> lessonTasks = lessonService.getAllTasksForLesson(lessonId);

        for (var userInternship : activeUsers) {
            User user = userInternship.getUser();
            for (var task : lessonTasks) {
                // отправленные пользователем решения для задачи
                List<UserTask> userTaskSolutions = solutionForLessonTasks.stream().filter(userTask ->
                                userTask.getUser().getId() == user.getId() && userTask.getTask().getId() == task.getId())
                        .collect(Collectors.toList());
                // если задача ещё не принята
                if (userTaskSolutions.stream().noneMatch(userTask -> userTask.getStatus().equals(TaskStatus.PASSED))) {
                    // получаем коммиты для задачи
                    List<GitlabCommit> commits = gitlabClient.getCommitsForProject(
                            getRepositoryNameForUsername(user.getUsername(), task.getRepository()),
                            privateToken);
                    // последний коммит по дате создания
                    Optional<GitlabCommit> lastCommit = getLastCreatedCommit(commits);
                    lastCommit.ifPresent(commit -> {
                        // дата создания коммита на момент последней проверки
                        Optional<UserTask> lastCheckedCommit = getLastUserTaskByCommitCreatedDate(userTaskSolutions);
                        if ((lastCheckedCommit.isEmpty()
                                || commit.getCreatedAt().after(lastCheckedCommit.get().getCommitCreatedAt()))) {
                            lastCommits.add(toUserTaskCommit(user, task, commit));
                        }
                    });
                }
            }
        }
        return lastCommits;
    }

    private void writeMessagesToAdminAboutNoForked(Set<User> admins, String taskName, int participantId) {
        admins.forEach(admin -> {
            messageService.sendMessageFromUserToUserWithText(1, admin.getId(),
                    "fork task with name " + taskName + "  for participant with id "
                            + participantId + " went wrong");
        });
    }

    private UserTaskCommit toUserTaskCommit(User user, Task task, GitlabCommit gitlabCommit) {
        UserTaskCommit userTaskCommit = new UserTaskCommit();
        userTaskCommit.setTaskId(task.getId());
        userTaskCommit.setTaskName(task.getName());
        userTaskCommit.setCommitAuthor(gitlabCommit.getAuthorName());
        userTaskCommit.setCommitUrl(gitlabCommit.getWebUrl());
        userTaskCommit.setLastCommitCreatedAt(gitlabCommit.getCreatedAt());
        userTaskCommit.setUsername(user.getUsername());
        return userTaskCommit;
    }

    /**
     * В БД эталонный репозиторий таска хранится как root/project
     * Этот метод меняет namespace пользователя эталонного репозитория на username участника стажировки
     *
     * @return относительный путь к репозиторию пользователя для задачи
     */
    private String getRepositoryNameForUsername(String username, String projectPathWithNamespace) {
        int namespaceEnd = projectPathWithNamespace.indexOf("/");
        return username + projectPathWithNamespace.substring(namespaceEnd);
    }

    Optional<GitlabCommit> getLastCreatedCommit(List<GitlabCommit> commits) {
        return commits.stream().max((commit1, commit2) ->
                commit1.getCreatedAt().after(commit2.getCreatedAt()) ? 1 : -1);
    }

    Optional<UserTask> getLastUserTaskByCommitCreatedDate(List<UserTask> userTasks) {
        return userTasks.stream().max((solution1, solution2) ->
                solution1.getCommitCreatedAt().after(solution2.getCommitCreatedAt()) ? 1 : -1);
    }

}
