package com.sweetshare.auth;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.stereotype.Component;

import com.sweetshare.common.error.ErrorCode;
import com.sweetshare.common.error.ErrorResponseWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ErrorResponseWriter errorResponseWriter;

    JsonAccessDeniedHandler(ErrorResponseWriter errorResponseWriter) {
        this.errorResponseWriter = errorResponseWriter;
    }

    public void handle(HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        ErrorCode errorcode = accessDeniedException instanceof CsrfException ? ErrorCode.CSRF_TOKEN_INVALID
                : ErrorCode.FORBIDDEN;
        errorResponseWriter.write(response, errorcode);
    }
}
