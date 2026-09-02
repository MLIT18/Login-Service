package com.insightzz.loginservice.service;

import com.insightzz.loginservice.client.ProjectAuthorizationClient;
import com.insightzz.loginservice.dto.LoginRequest;
import com.insightzz.loginservice.dto.LoginResponse;
import com.insightzz.loginservice.dto.UserAuthorizationResponse;
import com.insightzz.loginservice.entity.User;
import com.insightzz.loginservice.exception.InactiveUserException;
import com.insightzz.loginservice.exception.InvalidCredentialsException;
import com.insightzz.loginservice.repository.UserRepository;
import com.insightzz.loginservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final RolePermissionService rolePermissionService;

    private final ProjectAuthorizationClient
            projectAuthorizationClient;
    private final ProjectAuthorizationClientService
            projectAuthorizationClientService;

//    @Value("${internal.security.api-key}")
//    private String internalApiKey;
    @Override
    public LoginResponse login(LoginRequest request) {

        // =====================================================
        // 1. FIND USER
        // =====================================================

        User user =
                userRepository
                        .findByUserEmail(
                                request.getUserEmail()
                        )
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid username or password"
                                )
                        );


        // =====================================================
        // 2. CHECK USER ACTIVE
        // =====================================================

        if (user.getIsActive() == null ||
                user.getIsActive() != 1) {

            throw new InactiveUserException(
                    "User account is inactive"
            );
        }


        // =====================================================
        // 3. VERIFY PASSWORD
        // =====================================================

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }


        // =====================================================
        // 4. GET ROLE
        // =====================================================

        if (user.getRole() == null) {

            throw new InvalidCredentialsException(
                    "User role is not configured"
            );
        }


        String role =
                user.getRole()
                        .getRoleName()
                        .toUpperCase();


        // =====================================================
        // 5. GET GLOBAL ROLE PERMISSIONS
        // =====================================================

        List<String> permissions =
                rolePermissionService
                        .getPermissions(role);


        // =====================================================
        // 6. GET PROJECT/STAGE AUTHORIZATION
        // =====================================================

        UserAuthorizationResponse authorization =
                projectAuthorizationClientService
                        .getAuthorization(
                                user.getUserId()
                        );


        // =====================================================
        // 7. GENERATE JWT
        // =====================================================

        String token =
                jwtUtil.generateToken(
                        user.getUserId(),
                        user.getUserEmail(),
                        role,
                        permissions,
                        authorization,
                        user.getTokenVersion()
                );


        // =====================================================
        // 8. RETURN LOGIN RESPONSE
        // =====================================================

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(
                        jwtUtil.getExpirationSeconds()
                )
                .userId(user.getUserId())
                .username(user.getUserEmail())
                .role(role)
                .designation(user.getUserDesignation())
                .build();
    }
}