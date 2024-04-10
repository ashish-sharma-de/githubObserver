package com.secfix.githubObserver.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Github Observer API")
                        .version("1.0")
                        .description("This is a Github Observer API RESTful service using springdoc-openapi and OpenAPI 3."));
    }
}

