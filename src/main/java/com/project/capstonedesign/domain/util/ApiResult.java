package com.project.capstonedesign.domain.util;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final T data;
    private final String message;

    private ApiResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data, null);
    }

    public static <T> ApiResult<T> fail(Throwable throwable) {
        return new ApiResult<>(null, throwable.getMessage());
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(null, message);
    }
}
