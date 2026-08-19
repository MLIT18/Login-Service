package com.insightzz.loginservice.service;

import com.insightzz.loginservice.dto.LoginRequest;
import com.insightzz.loginservice.dto.LoginResponse;
import com.insightzz.loginservice.entity.User;
import com.insightzz.loginservice.exception.InactiveUserException;
import com.insightzz.loginservice.exception.InvalidCredentialsException;
import com.insightzz.loginservice.repository.UserRepository;
import com.insightzz.loginservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
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

    @Override
    public LoginResponse login(LoginRequest request) {

        // =====================================================
        // 1. FIND USER
        // =====================================================

        User user = userRepository
                .findByUserName(request.getUsername())
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

        String role = user.getUserRole();

        if (role == null || role.isBlank()) {

            throw new InvalidCredentialsException(
                    "User role is not configured"
            );
        }

        role = role.toUpperCase();


        // =====================================================
        // 5. GET ROLE PERMISSIONS
        // =====================================================

        List<String> permissions =
                rolePermissionService.getPermissions(role);


        // =====================================================
        // 6. GENERATE JWT
        // =====================================================

        String token = jwtUtil.generateToken(
                user.getUserId(),
                user.getUserName(),
                role,
                permissions
        );


        // =====================================================
        // 7. RETURN LOGIN RESPONSE
        // =====================================================

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpirationSeconds())
                .userId(user.getUserId())
                .username(user.getUserName())
                .role(role)
                .designation(user.getUserDesignation())
                .build();
    }
}