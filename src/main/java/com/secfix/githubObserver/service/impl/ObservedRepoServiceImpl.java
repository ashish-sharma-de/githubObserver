package com.secfix.githubObserver.service.impl;

import com.secfix.githubObserver.config.RabbitConfig;
import com.secfix.githubObserver.model.GithubRepo;
import com.secfix.githubObserver.model.ObservedRepo;
import com.secfix.githubObserver.repository.entity.ObservedRepoEntity;
import com.secfix.githubObserver.repository.ObservedRepoRepository;
import com.secfix.githubObserver.repository.entity.ObservedRepoStatus;
import com.secfix.githubObserver.service.ObservedRepoService;
import com.secfix.githubObserver.util.ModelToEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
public class ObservedRepoServiceImpl implements ObservedRepoService {

    private final ObservedRepoRepository repository;
    private final GithubClient gitHubClient;

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ObservedRepoServiceImpl.class);


    public ObservedRepoServiceImpl(ObservedRepoRepository repository, GithubClient gitHubClient, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.gitHubClient = gitHubClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createObservedRepo(ObservedRepo observedRepo) {
        ObservedRepoEntity observedRepoEntity = ModelToEntityMapper.mapObservedRepoToEntity(observedRepo);
        repository.save(observedRepoEntity);
    }

    @Override
    public void deleteObservedRepo(String id) {
        ObservedRepoEntity entity = repository.getReferenceById(id);
        entity.setStatus(ObservedRepoStatus.DELETED);
        repository.save(entity);
    }

    @Override
    public ObservedRepo updateObservedRepo(ObservedRepo observedRepo) {
        ObservedRepoEntity entity = repository.getReferenceById(observedRepo.getId());
        entity.setName(observedRepo.getName() != null ? observedRepo.getName() : entity.getName());
        entity.setUrl(observedRepo.getUrl() != null ? observedRepo.getUrl() : entity.getUrl());
        entity.setStatus(observedRepo.getStatus() != null ? ObservedRepoStatus.valueOf(observedRepo.getStatus()) : entity.getStatus());
        entity.setOwner(observedRepo.getOwner() != null ? observedRepo.getOwner() : entity.getOwner());
        entity.setStars(observedRepo.getStars() != observedRepo.getStars() ? observedRepo.getStars() : entity.getStars());
        entity.setOpenIssues(observedRepo.getOpenIssues() != observedRepo.getOpenIssues() ? observedRepo.getOpenIssues() : entity.getOpenIssues());
        entity.setLicense(observedRepo.getLicense() != null ? observedRepo.getLicense() : entity.getLicense());
        entity.setCreatedAt(observedRepo.getCreatedAt() != null ? ModelToEntityMapper.convertToLocalDateTime(observedRepo.getCreatedAt()) : entity.getCreatedAt());
        entity.setUpdatedAt(observedRepo.getUpdatedAt() != null ? ModelToEntityMapper.convertToLocalDateTime(observedRepo.getUpdatedAt()) : entity.getUpdatedAt());
        return ModelToEntityMapper.mapObservedRepoToModel(repository.save(entity));
    }

    @Override
    public ObservedRepo getObservedRepo(String id) {
        ObservedRepoEntity entity = repository.getReferenceById(id);
        if (entity != null) {
            return ModelToEntityMapper.mapObservedRepoToModel(entity);
        }
        return null;
    }

    @Override
    public void fetchAndUpdateObservedRepo() {
        Pageable pageable = PageRequest.of(0, 20); // Example pagination configuration
        boolean hasNext;
        do {
            Page<ObservedRepoEntity> page = repository.findAllByStatus(ObservedRepoStatus.ACTIVE, pageable);
            List<ObservedRepoEntity> repos = page.getContent();
            for (ObservedRepoEntity repo : repos) {
                GithubRepo gitHubRepo = gitHubClient.fetchRepoData(repo.getOwner(), repo.getName());
                if (gitHubRepo == null) {
                    invalidateObservedRepo(repo);
                    continue;
                }
                updateObservedRepo(repo, gitHubRepo);
            }
            hasNext = page.hasNext();
            pageable = page.nextPageable();
        } while (hasNext);
    }

    private void invalidateObservedRepo(ObservedRepoEntity observedRepo) {
        observedRepo.setStatus(ObservedRepoStatus.INVALID);
        repository.save(observedRepo);
        logger.info("Invalidated observed repo: " + observedRepo.getName());
    }

    private void updateObservedRepo(ObservedRepoEntity observedRepo, GithubRepo gitHubRepo) {
        observedRepo = mapGithubRepoToObservedRepo(gitHubRepo, observedRepo);
        repository.save(observedRepo);
        logger.info("Updated observed repo: " + observedRepo.getName());
        rabbitTemplate.convertAndSend(RabbitConfig.queueName, ModelToEntityMapper.mapObservedRepoToModel(observedRepo));
        logger.info("Sent update for observed repo: " + observedRepo.getName());
    }

    private ObservedRepoEntity mapGithubRepoToObservedRepo(GithubRepo gitHubRepo, ObservedRepoEntity observedRepo) {
        if (!observedRepo.getName().equals(gitHubRepo.getName())) {
            observedRepo.setName(gitHubRepo.getName());
        }
        if (!observedRepo.getUrl().equals(gitHubRepo.getUrl())) {
            observedRepo.setUrl(gitHubRepo.getUrl());
        }
        if (!observedRepo.getOwner().equals(gitHubRepo.getOwner())) {
            observedRepo.setOwner(gitHubRepo.getOwner());
        }
        if (observedRepo.getStars() != gitHubRepo.getStars()) {
            observedRepo.setStars(gitHubRepo.getStars());
        }
        if (observedRepo.getOpenIssues() != gitHubRepo.getOpenIssues()) {
            observedRepo.setOpenIssues(gitHubRepo.getOpenIssues());
        }
        if (!observedRepo.getLicense().equals(gitHubRepo.getLicense())) {
            observedRepo.setLicense(gitHubRepo.getLicense());
        }
        if (observedRepo.getCreatedAt() != gitHubRepo.getCreatedAt()) {
            observedRepo.setCreatedAt(gitHubRepo.getCreatedAt());
        }
        if (observedRepo.getUpdatedAt() != gitHubRepo.getUpdatedAt()) {
            observedRepo.setUpdatedAt(gitHubRepo.getUpdatedAt());
        }
        return observedRepo;
    }

}
