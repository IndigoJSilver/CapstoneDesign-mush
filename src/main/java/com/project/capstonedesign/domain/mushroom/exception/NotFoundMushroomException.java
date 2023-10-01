package com.project.capstonedesign.domain.mushroom.exception;

public class NotFoundMushroomException extends RuntimeException {

    public NotFoundMushroomException(String msg) {
        super(msg);
    }

    public NotFoundMushroomException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
