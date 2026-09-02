package com.insightzz.loginservice.service;

import com.insightzz.loginservice.client.ProjectAuthorizationClient;
import com.insightzz.loginservice.dto.UserAuthorizationResponse;
import com.insightzz.loginservice.exception.ProjectAuthorizationUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectAuthorizationClientService {

    private final ProjectAuthorizationClient projectAuthorizationClient;

    @CircuitBreaker(
            name = "projectStageService",
            fallbackMethod = "projectAuthorizationFallback"
    )
    public UserAuthorizationResponse getAuthorization(
            Long userId) {

        return projectAuthorizationClient
                .getUserAuthorization(userId);
    }

    private UserAuthorizationResponse projectAuthorizationFallback(
            Long userId,
            Throwable throwable) {

        throw new ProjectAuthorizationUnavailableException(
                "Project authorization service is currently unavailable. " +
                        "Please try again later."
        );
    }
}
