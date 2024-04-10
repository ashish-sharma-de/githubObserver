package com.secfix.githubObserver.repository;

import com.secfix.githubObserver.repository.entity.ObservedRepoEntity;
import com.secfix.githubObserver.repository.entity.ObservedRepoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservedRepoRepository extends JpaRepository<ObservedRepoEntity, String> {
    public Page<ObservedRepoEntity> findAllByStatus(ObservedRepoStatus status, Pageable pageable);
}
