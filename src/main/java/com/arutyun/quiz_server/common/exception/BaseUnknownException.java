package com.arutyun.quiz_server.common.exception;

public abstract class BaseUnknownException extends BaseException {
    protected BaseUnknownException(String error, String message) {
        super(500, error, message);
    }
}