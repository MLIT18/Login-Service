package com.insightzz.loginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationResponse {

    private Long userId;
    private List<String> authorities;
    private List<ProjectAuthorizationResponse> projects;
}
