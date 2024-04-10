package com.secfix.githubObserver.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservedRepoEntity {
    @Id
    private String id;
    private String url;
    private String owner;
    private String name;
    private int stars;
    private int openIssues;
    private String license;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ObservedRepoStatus status;
}
