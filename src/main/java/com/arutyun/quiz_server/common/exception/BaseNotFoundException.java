package com.arutyun.quiz_server.common.exception;

public abstract class BaseNotFoundException extends BaseException {
    protected BaseNotFoundException(String error, String message) {
        super(404, error, message);
    }
}
