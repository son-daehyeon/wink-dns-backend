package com.github.son_daehyeon.common.api;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private final int statusCode;
    private final String error;
    private final T content;

    private ApiResponse(int statusCode, String error, T content) {

        this.statusCode = statusCode;
        this.error = error;
        this.content = content;
    }

    public static <T> ApiResponse<T> ok() {

        return new ApiResponse<>(200, null, null);
    }

    public static <T> ApiResponse<T> ok(T content) {

        return new ApiResponse<>(200, null, content);
    }

    public static ApiResponse<Map<String, String>> error(ApiException exception) {

        return error(exception.getStatus(), exception.getMessage());
    }

    public static ApiResponse<Map<String, String>> error(HttpStatus status, String message) {

        return new ApiResponse<>(status.value(), message, null);
    }
}
