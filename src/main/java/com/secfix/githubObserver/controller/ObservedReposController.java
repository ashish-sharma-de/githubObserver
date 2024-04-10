package com.secfix.githubObserver.controller;

import com.secfix.githubObserver.model.ObservedRepo;
import com.secfix.githubObserver.service.ObservedRepoService;
import com.secfix.githubObserver.service.impl.GithubClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/observed-repos")
public class ObservedReposController {
    private final ObservedRepoService observedRepoService;
    private static final Logger logger = LoggerFactory.getLogger(ObservedReposController.class);


    public ObservedReposController(ObservedRepoService observedRepoService) {
        this.observedRepoService = observedRepoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createObservedRepo(@RequestBody ObservedRepo observedRepo) {
        logger.info("Creating observed repo: " + observedRepo.getName());
        observedRepoService.createObservedRepo(observedRepo);
        return ResponseEntity.ok("Repo observed");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getObservedRepo(@PathVariable String id) {
        logger.info("Getting observed repo: " + id);
        ObservedRepo observedRepo = observedRepoService.getObservedRepo(id);
        if (observedRepo != null) {
            return ResponseEntity.ok(observedRepo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObservedRepo(@PathVariable String id) {
        logger.info("Deleting observed repo: " + id);
        ObservedRepo observedRepo = observedRepoService.getObservedRepo(id);
        if (observedRepo != null) {
            observedRepoService.deleteObservedRepo(id);
            return ResponseEntity.ok("Repo deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateObserved(@RequestBody ObservedRepo observedRepo) {
        logger.info("Updating observed repo: " + observedRepo.getName());
        ObservedRepo isPresent = observedRepoService.getObservedRepo(observedRepo.getId());
        if (isPresent != null) {
            return ResponseEntity.ok(observedRepoService.updateObservedRepo(observedRepo));
        }
        return ResponseEntity.notFound().build();
    }
}
