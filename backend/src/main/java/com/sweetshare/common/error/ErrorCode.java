package com.sweetshare.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Login required"),
    CSRF_TOKEN_INVALID(HttpStatus.FORBIDDEN, "Missing or invalid CSRF token"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access denied"),

    EMAIL_TAKEN(HttpStatus.CONFLICT, "Email is already registered"),
    USERNAME_TAKEN(HttpStatus.CONFLICT, "Username is already taken"),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "Request validation failed")

    MALFORMED_REQUEST(HttpStatus.BAD_REQUEST, "Malformed request body"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

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
