package com.insightzz.loginservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenVersionResponse {

    private Long userId;

    private Boolean active;

    private Long tokenVersion;
}