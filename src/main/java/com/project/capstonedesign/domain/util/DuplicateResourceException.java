package com.project.capstonedesign.domain.util;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String msg) {
        super(msg);
    }

    public DuplicateResourceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
