package com.secfix.githubObserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GithubRepo {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("html_url")
    private String url;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stargazers_count")
    private Integer stars;

    @JsonProperty("open_issues_count")
    private Integer openIssues;

    @JsonProperty("license")
    private String license;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    // Constructors, Getters, and Setters

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {
        @JsonProperty("login")
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class License {
        @JsonProperty("key")
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public void setOwner(Owner owner) {
        this.owner = owner.getLogin();
    }

    public void setLicense(License license) {
        this.license = license.getKey();
    }


}

