package com.lucasconte.tenpochallenge.error;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public final class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String stackTrace;

    private ApiError(Date timestamp, int code, String status, String message, String stackTrace) {
        this.timestamp = timestamp;
        this.code = code;
        this.status = status;
        this.message = message;
        this.stackTrace = stackTrace;
    }

    private static ApiError of(HttpStatus httpStatus, Exception exception) {
        return new ApiError(
                new Date(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage(),
                Arrays.stream(ThrowableToStringArray.convert(exception)).collect(Collectors.joining())
        );
    }

    public static ApiError of(HttpStatus httpStatus, String message, Exception exception) {
        return new ApiError(
                new Date(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                Arrays.stream(ThrowableToStringArray.convert(exception)).collect(Collectors.joining())
        );
    }

    public static ApiError internalServerError(Exception exception) {
        return ApiError.of(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    public static ApiError unauthorized(Exception exception) {
        return ApiError.of(HttpStatus.UNAUTHORIZED, exception);
    }

    public static ApiError conflict(Exception exception) {
        return ApiError.of(HttpStatus.CONFLICT, exception);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
