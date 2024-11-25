package com.arutyun.quiz_server.common.exception;

public abstract class BaseConflictException extends BaseException {
    protected BaseConflictException(String error, String message) {
        super(409, error, message);
    }
}