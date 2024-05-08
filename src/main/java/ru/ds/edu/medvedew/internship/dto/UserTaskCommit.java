package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("Последний коммита оставленный пользователем для задачи")
@Data
public class UserTaskCommit {
    private int taskId;
    private String taskName;
    private String username;
    private Date lastCommitCreatedAt;
    private String commitAuthor;
    private String commitUrl;
}
