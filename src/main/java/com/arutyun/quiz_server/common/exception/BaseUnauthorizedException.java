package com.arutyun.quiz_server.common.exception;

public abstract class BaseUnauthorizedException extends BaseException {
    protected BaseUnauthorizedException(String error, String message) {
        super(401, error, message);
    }
}