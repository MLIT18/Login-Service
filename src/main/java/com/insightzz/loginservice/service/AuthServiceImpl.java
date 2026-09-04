package com.insightzz.loginservice.service;

import com.insightzz.loginservice.dto.LoginRequest;
import com.insightzz.loginservice.dto.LoginResponse;
import com.insightzz.loginservice.dto.UserAuthorizationResponse;
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

    private final ProjectAuthorizationClientService
            projectAuthorizationClientService;

    @Override
    public LoginResponse login(
            LoginRequest request) {

        /*
         * ------------------------------------------
         * 1. Find user
         * ------------------------------------------
         */

        User user =
                userRepository
                        .findByUserEmail(
                                request.getUserEmail()
                        )
                        .orElseThrow(
                                () -> new InvalidCredentialsException(
                                        "Invalid username or password"
                                )
                        );


        /*
         * ------------------------------------------
         * 2. Active check
         * ------------------------------------------
         */

        if (user.getIsActive() == null ||
                user.getIsActive() != 1) {

            throw new InactiveUserException(
                    "User account is inactive"
            );
        }


        /*
         * ------------------------------------------
         * 3. Password check
         * ------------------------------------------
         */

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }


        /*
         * ------------------------------------------
         * 4. Role check
         * ------------------------------------------
         */

        if (user.getRole() == null) {

            throw new InvalidCredentialsException(
                    "User role is not configured"
            );
        }


        /*
         * ------------------------------------------
         * 5. Get role + roleId
         * ------------------------------------------
         */

        String role =
                user.getRole()
                        .getRoleName()
                        .toUpperCase();

        Integer roleId =
                user.getRole()
                        .getId();


        /*
         * ------------------------------------------
         * 6. Get authorization from
         *    project-stage-service
         * ------------------------------------------
         */

        UserAuthorizationResponse authorization =
                projectAuthorizationClientService
                        .getAuthorization(
                                user.getUserId(),
                                roleId
                        );


        /*
         * ------------------------------------------
         * 7. Authorities now come from
         *    project-stage-service
         * ------------------------------------------
         */

        List<String> authorities =
                authorization != null &&
                        authorization.getAuthorities() != null

                        ? authorization.getAuthorities()

                        : List.of();


        /*
         * ------------------------------------------
         * 8. Generate JWT
         * ------------------------------------------
         */

        String token =
                jwtUtil.generateToken(
                        user.getUserId(),
                        user.getUserEmail(),
                        role,
                        authorities,
                        authorization,
                        user.getTokenVersion()
                );


        /*
         * ------------------------------------------
         * 9. Response
         * ------------------------------------------
         */

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