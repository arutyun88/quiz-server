package com.arutyun.quiz_server.common.exception;

public abstract class BaseForbiddenException extends BaseException {
    protected BaseForbiddenException(String error, String message) {
        super(403, error, message);
    }
}