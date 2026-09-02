package com.insightzz.loginservice.dto;

import com.insightzz.loginservice.enums.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAuthorizationResponse {

    private Integer projectId;

    private Long projectPoId;

    private AccessLevel projectAccess;

    private List<StageAuthorizationResponse> stages;
}
