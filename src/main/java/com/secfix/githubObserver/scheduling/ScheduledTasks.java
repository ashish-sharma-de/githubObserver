package com.secfix.githubObserver.scheduling;

import com.secfix.githubObserver.service.ObservedRepoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ObservedRepoService observedRepoService;

    public ScheduledTasks(ObservedRepoService observedRepoService) {
        this.observedRepoService = observedRepoService;
    }

    @Scheduled(fixedDelayString = "${scheduled.fixedDelayMillis}")
    public void performTask() {
        System.out.println("Scheduled task executed.");
        observedRepoService.fetchAndUpdateObservedRepo();
    }
}
