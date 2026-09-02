package com.insightzz.loginservice.dto;

import com.insightzz.loginservice.enums.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageAuthorizationResponse {

    private Integer stageId;

    private String stageCode;

    private String stageName;

    private AccessLevel accessLevel;
}
