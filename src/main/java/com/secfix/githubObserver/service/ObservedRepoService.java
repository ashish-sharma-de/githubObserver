package com.secfix.githubObserver.service;

import com.secfix.githubObserver.model.ObservedRepo;

import java.util.List;

public interface ObservedRepoService {

    public void createObservedRepo(ObservedRepo observedRepo);
    public void deleteObservedRepo(String id);
    public ObservedRepo updateObservedRepo(ObservedRepo observedRepo);
    public ObservedRepo getObservedRepo(String id);

    public void fetchAndUpdateObservedRepo();
}
