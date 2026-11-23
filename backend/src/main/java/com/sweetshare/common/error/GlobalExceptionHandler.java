package com.sweetshare.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorCode errorCode = ex.errorCode();
        return ResponseEntity.status(errorCode.status()).body(ErrorResponse.of(errorCode));
    }
    
}
