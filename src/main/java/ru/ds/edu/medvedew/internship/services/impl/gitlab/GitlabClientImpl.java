package ru.ds.edu.medvedew.internship.services.impl.gitlab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.ds.edu.medvedew.internship.dto.gitlab.GitlabCommit;
import ru.ds.edu.medvedew.internship.dto.gitlab.GitlabUserCreateDto;
import ru.ds.edu.medvedew.internship.dto.gitlab.GitlabUserDto;
import ru.ds.edu.medvedew.internship.dto.gitlab.ProjectDto;
import ru.ds.edu.medvedew.internship.exceptions.checked.gitlab.GitlabClientException;
import ru.ds.edu.medvedew.internship.services.gitlab.GitlabClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitlabClientImpl implements GitlabClient {
    private final RestTemplate restTemplate;
    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Override
    public void forkProject(String projectPathWithNamespace, String toNamespace, String privateToken)
            throws GitlabClientException {
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
            throw new GitlabClientException(e);
        }
    }

    @Override
    public void createUser(String name, String username, String email, String password, String privateToken)
            throws GitlabClientException {
        String url = gitlabUrl + "/api/v4/users";
        GitlabUserCreateDto gitlabUserCreateDto = new GitlabUserCreateDto(name, email, username, password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("PRIVATE-TOKEN", privateToken);

        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(gitlabUserCreateDto, httpHeaders),
                    GitlabUserDto.class);
        } catch (Exception e) {
            log.error("Create gitlab user for user with username {} went wrong. Exception: {}", username,
                    e.getMessage());
            throw new GitlabClientException(e);
        }
    }

    @Override
    public List<GitlabCommit> getCommitsForProject(String projectPathWithNamespace, String privateToken)
            throws GitlabClientException {
        String projectPathEncoded = URLEncoder.encode(projectPathWithNamespace, StandardCharsets.UTF_8);
        String url = gitlabUrl + "/api/v4/projects/" + projectPathEncoded + "/repository/commits";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("PRIVATE-TOKEN", privateToken);

        try {
            ResponseEntity<List<GitlabCommit>> commits = restTemplate.exchange(URI.create(url), HttpMethod.GET,
                    new HttpEntity<>(httpHeaders),
                    new ParameterizedTypeReference<>() {
                    });
            return commits.getBody();
        } catch (Exception e) {
            log.error("Get gitlab repository commits with path {} went wrong. Exception: {}", projectPathEncoded,
                    e.getMessage());
            throw new GitlabClientException(e);
        }
    }

}
