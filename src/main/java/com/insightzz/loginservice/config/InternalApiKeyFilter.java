package com.insightzz.loginservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InternalApiKeyFilter
        extends OncePerRequestFilter {

    @Value("${internal.security.api-key}")
    private String expectedApiKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path =
                request.getRequestURI();

        // =====================================================
        // ONLY INTERNAL ENDPOINTS
        // =====================================================

        if (path.startsWith("/internal/")) {

            String apiKey =
                    request.getHeader(
                            "X-Internal-Api-Key"
                    );

            if (apiKey == null ||
                    !expectedApiKey.equals(apiKey)) {

                response.setStatus(
                        HttpServletResponse.SC_FORBIDDEN
                );

                response.setContentType(
                        "application/json"
                );

                response.getWriter().write(
                        """
                        {
                            "status": 403,
                            "error": "FORBIDDEN",
                            "message": "Invalid internal API key"
                        }
                        """
                );

                return;
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}