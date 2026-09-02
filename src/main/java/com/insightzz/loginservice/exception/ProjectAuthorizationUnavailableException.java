package com.insightzz.loginservice.exception;

public class ProjectAuthorizationUnavailableException
        extends RuntimeException {

    public ProjectAuthorizationUnavailableException(
            String message) {

        super(message);
    }
}