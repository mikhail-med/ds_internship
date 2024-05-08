package ru.ds.edu.medvedew.internship.dto.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("Коммит полученный с gitlab")
@Data
public class GitlabCommit {
    private String id;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("web_url")
    private String webUrl;
}
