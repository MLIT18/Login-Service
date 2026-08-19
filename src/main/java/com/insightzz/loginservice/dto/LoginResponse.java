package com.insightzz.loginservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private long expiresIn;

    private Integer userId;

    private String username;

    private String role;

    private String designation;
}
