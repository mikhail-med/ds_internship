package ru.ds.edu.medvedew.internship.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ds.edu.medvedew.internship.dto.UserTaskCommit;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabService;

import java.util.List;

@RestController
@RequestMapping("/v1/gitlab")
@Api(tags = "Взаимодействие с gitlab")
@RequiredArgsConstructor
public class GitlabController {
    private final GitlabService gitlabService;

    @PostMapping("/publish-lesson/{lessonId}")
    @ApiOperation("Публикация занятия")
    public void publishLesson(@PathVariable int lessonId, @RequestHeader("PRIVATE-TOKEN") String privateToken) {
        gitlabService.publishLesson(lessonId, privateToken);
    }

    @PostMapping("/users/{id}")
    @ApiOperation("Создание пользователя в gitlab по id юзера стажировки")
    public void createGitlabUser(@PathVariable int id, @RequestHeader("PRIVATE-TOKEN") String privateToken) {
        gitlabService.createGitlabAccountForUser(id, privateToken);
    }

    @GetMapping("/lessons/{id}/tasks/last-commits")
    @ApiOperation("Получить свежие коммиты для непрооверенных или непринятых решений задач некоторого занятия")
    public List<UserTaskCommit> getNewCommits(@PathVariable int id, @RequestHeader("PRIVATE-TOKEN") String privateToken) {
        return gitlabService.getNewUncheckedCommitsForLesson(id, privateToken);
    }
}
