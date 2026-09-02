package com.insightzz.loginservice.controller;


import com.insightzz.loginservice.dto.LoginRequest;
import com.insightzz.loginservice.dto.LoginResponse;
import com.insightzz.loginservice.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //public AuthController(AuthService authService) {
     //   this.authService = authService;
    //}

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
