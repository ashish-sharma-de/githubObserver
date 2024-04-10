package com.secfix.githubObserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObservedRepo {
    private String id;
    private String url;
    private String status;
    private String owner;
    private String name;
    private int stars;
    private int openIssues;
    private String license;
    private String createdAt;
    private String updatedAt;
}
