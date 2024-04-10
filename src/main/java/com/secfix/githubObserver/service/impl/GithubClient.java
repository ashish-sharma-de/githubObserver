package com.secfix.githubObserver.service.impl;

import com.secfix.githubObserver.model.GithubRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient {
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(GithubClient.class);

    public GithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GithubRepo fetchRepoData(String owner, String name) {
        String url = "https://api.github.com/repos/" + owner + "/" + name;
        try {
            ResponseEntity<GithubRepo> response = restTemplate.getForEntity(url, GithubRepo.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Repo not found: " + owner + "/" + name);
            return null;
        } catch (HttpClientErrorException e) {
            logger.error("Error fetching repo data: " + e.getMessage());
            throw e;
        }
    }
}