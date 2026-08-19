package com.insightzz.loginservice.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionService {

    public List<String> getPermissions(String role) {

        return switch (role.toUpperCase()) {

            case "ADMIN" -> List.of(
                    "USER_CREATE",
                    "USER_READ",
                    "USER_UPDATE",
                    "USER_DELETE",
                    "USER_DISABLE"
            );

            case "PROJECT_MANAGER" -> List.of(
                    "USER_READ"
            );

            default -> List.of();
        };
    }
}
