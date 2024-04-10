package com.secfix.githubObserver.util;

import com.secfix.githubObserver.model.ObservedRepo;
import com.secfix.githubObserver.repository.entity.ObservedRepoEntity;
import com.secfix.githubObserver.repository.entity.ObservedRepoStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ModelToEntityMapper {

    public static ObservedRepoEntity mapObservedRepoToEntity(ObservedRepo model) {
        ObservedRepoEntity entity = ObservedRepoEntity.builder().
                id(model.getId()).
                url(model.getUrl()).
                status(ObservedRepoStatus.valueOf(model.getStatus().toUpperCase())).
                owner(model.getOwner()).
                name(model.getName()).
                stars(model.getStars()).
                openIssues(model.getOpenIssues()).
                license(model.getLicense()).
                createdAt(convertToLocalDateTime(model.getCreatedAt())).
                updatedAt(convertToLocalDateTime(model.getUpdatedAt())).
                build();
        return entity;
    }

    public static LocalDateTime convertToLocalDateTime(String dateTime) {
        return Instant.parse(dateTime).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static ObservedRepo mapObservedRepoToModel(ObservedRepoEntity entity) {
        ObservedRepo model = ObservedRepo.builder().
                id(entity.getId()).
                url(entity.getUrl()).
                status(entity.getStatus().getValue()).
                owner(entity.getOwner()).
                name(entity.getName()).
                stars(entity.getStars()).
                openIssues(entity.getOpenIssues()).
                license(entity.getLicense()).
                createdAt(entity.getCreatedAt().toString()).
                updatedAt(entity.getUpdatedAt().toString()).
                build();
        return model;
    }
}
