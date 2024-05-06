package ru.ds.edu.medvedew.internship.services.impl.gitlab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.ds.edu.medvedew.internship.dto.gitlab.ProjectDto;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitlabClientImpl implements GitlabClient {
    private final RestTemplate restTemplate;
    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Override
    public boolean forkProject(String projectPathWithNamespace, String toNamespace, String privateToken) {
        // преобразование из user/project в user%2Fproject
        String projectPathEncoded = URLEncoder.encode(projectPathWithNamespace, StandardCharsets.UTF_8);
        String url = gitlabUrl + "/api/v4/projects/" + projectPathEncoded
                + "/fork?namespace_path=" + toNamespace;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("PRIVATE-TOKEN", privateToken);

        try {
            // URI.create тут необходим и важен, иначе с encoded project path не будет работать
            restTemplate.exchange(URI.create(url), HttpMethod.POST, new HttpEntity<>(httpHeaders),
                    ProjectDto.class);
        } catch (Exception e) {
            log.warn("fork project with path {} went wrong. Exception: {}",
                    projectPathWithNamespace, e.getMessage());
            return false;
        }
        return true;
    }
}
