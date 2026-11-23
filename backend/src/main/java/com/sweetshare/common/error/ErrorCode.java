package com.sweetshare.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Login required"),
    CSRF_TOKEN_INVALID(HttpStatus.FORBIDDEN, "Missing or invalid CSRF token"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access denied"),

    EMAIL_TAKEN(HttpStatus.CONFLICT, "Email is already registered"),
    USERNAME_TAKEN(HttpStatus.CONFLICT, "Username is already taken");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus status() {
        return status;
    }

    public String defaultMessage() {
        return defaultMessage;
    }
}
