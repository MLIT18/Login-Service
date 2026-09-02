package com.insightzz.loginservice.controller;

import com.insightzz.loginservice.dto.TokenVersionResponse;
import com.insightzz.loginservice.entity.User;
import com.insightzz.loginservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {

    private final UserRepository userRepository;


    @GetMapping("/users/{userId}/token-version")
    public ResponseEntity<TokenVersionResponse>
    getTokenVersion(
            @PathVariable Integer userId) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow();


        boolean active =
                user.getIsActive() != null &&
                        user.getIsActive() == 1;


        return ResponseEntity.ok(
                TokenVersionResponse.builder()
                        .userId(user.getUserId())
                        .active(active)
                        .tokenVersion(
                                user.getTokenVersion()
                        )
                        .build()
        );
    }
}