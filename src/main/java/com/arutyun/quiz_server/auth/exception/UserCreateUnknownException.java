package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseUnknownException;

public class UserCreateUnknownException extends BaseUnknownException {
    public UserCreateUnknownException(String message) {
        super("UNKNOWN_EXCEPTION", message);
    }
}
