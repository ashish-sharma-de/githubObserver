package com.secfix.githubObserver.repository.entity;

public enum ObservedRepoStatus {
    ACTIVE("Active"),
    DELETED("Deleted"),
    INVALID("Invalid");

    private final String value;
    ObservedRepoStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
