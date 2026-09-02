package com.insightzz.loginservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String userEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
