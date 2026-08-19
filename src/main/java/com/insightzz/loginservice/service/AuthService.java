package com.insightzz.loginservice.service;

import com.insightzz.loginservice.dto.LoginRequest;
import com.insightzz.loginservice.dto.LoginResponse;
import jakarta.validation.Valid;

public interface AuthService {
    LoginResponse login(@Valid LoginRequest request);
}
