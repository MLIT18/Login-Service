package com.insightzz.loginservice.client;

import com.insightzz.loginservice.config.InternalFeignConfig;
import com.insightzz.loginservice.dto.UserAuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "project-stage-service",
        url = "${services.project-stage.url}",
        configuration = InternalFeignConfig.class
)
public interface ProjectAuthorizationClient {

    @GetMapping("/internal/authorization/users/{userId}")
    UserAuthorizationResponse getUserAuthorization(
            @PathVariable("userId") Long userId
    );
}