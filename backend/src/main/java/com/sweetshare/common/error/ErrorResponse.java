package com.sweetshare.common.error;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorResponse(ErrorBody error) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErrorBody(String code, String message, List<FieldErrorDetail> details) {
    }

    public record FieldErrorDetail(String field, String message) {
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return of(errorCode, null);
    }

    public static ErrorResponse of(ErrorCode errorCode, List<FieldErrorDetail> details) {
        return new ErrorResponse(new ErrorBody(errorCode.name(), errorCode.defaultMessage(), details));
    }
}
