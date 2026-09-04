package com.insightzz.loginservice.util;

import com.insightzz.loginservice.dto.UserAuthorizationResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    private final long expiration;


    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.secretKey =
                Keys.hmacShaKeyFor(
                        secret.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        this.expiration = expiration;
    }


    public String generateToken(
            Long userId,
            String username,
            String role,
            List<String> authorities,
            UserAuthorizationResponse authorization,
            Long tokenVersion) {

        Date now = new Date();

        Date expiry =
                new Date(
                        now.getTime() + expiration
                );


        return Jwts.builder()

                .subject(username)

                .claim(
                        "userId",
                        userId
                )

                .claim(
                        "role",
                        role
                )

                .claim(
                        "authorities",
                        authorities != null
                                ? authorities
                                : List.of()
                )

                .claim(
                        "projectScopes",
                        authorization != null &&
                                authorization.getProjects() != null

                                ? authorization.getProjects()

                                : List.of()
                )

                .claim(
                        "tokenVersion",
                        tokenVersion
                )

                .issuedAt(now)

                .expiration(expiry)

                .signWith(secretKey)

                .compact();
    }


    public long getExpirationSeconds() {

        return expiration / 1000;
    }
}