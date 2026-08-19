package com.insightzz.loginservice.service;

import com.insightzz.loginservice.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Component
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    public List<String> getPermissions(String role) {

        return rolePermissionRepository
                .findPermissionsByRole(role);
    }
//    public List<String> getPermissions(String role) {
//
//        return switch (role.toUpperCase()) {
//
//            case "ADMIN" -> List.of(
//                    "USER_CREATE",
//                    "USER_READ",
//                    "USER_UPDATE",
//                    "USER_DELETE",
//                    "USER_DISABLE"
//            );
//
//            case "PROJECT_MANAGER" -> List.of(
//                    "USER_READ"
//            );
//
//            default -> List.of();
//        };
//    }
}
