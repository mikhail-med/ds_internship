package ru.ds.edu.medvedew.internship.services.impl.gitlab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.GitlabUserCantBeCreated;
import ru.ds.edu.medvedew.internship.models.*;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.services.LessonService;
import ru.ds.edu.medvedew.internship.services.MessageService;
import ru.ds.edu.medvedew.internship.services.RoleService;
import ru.ds.edu.medvedew.internship.services.UserService;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabClient;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    private void writeMessagesToAdminAboutNoForked(Set<User> admins, String taskName, int participantId) {
        admins.forEach(admin -> {
            messageService.sendMessageFromUserToUserWithText(1, admin.getId(),
                    "fork task with name " + taskName + "  for participant with id "
                            + participantId + " went wrong");
        });
    }

}
