package com.arutyun.quiz_server.common.exception;

public abstract class BaseBadRequestException extends BaseException {
    protected BaseBadRequestException(String error, String message) {
        super(400, error, message);
    }
}
