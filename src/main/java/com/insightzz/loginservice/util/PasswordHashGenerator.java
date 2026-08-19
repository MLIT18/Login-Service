package com.insightzz.loginservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String plainPassword = "Shubham@12345";

        String hashedPassword =
                encoder.encode(plainPassword);

        System.out.println("BCrypt Password:");
        System.out.println(hashedPassword);
    }
}