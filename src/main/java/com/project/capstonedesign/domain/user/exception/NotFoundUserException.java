package com.project.capstonedesign.domain.user.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String msg) {
        super(msg);
    }

    public NotFoundUserException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
