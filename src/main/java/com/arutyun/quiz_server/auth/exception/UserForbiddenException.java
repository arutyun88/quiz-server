package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseForbiddenException;

public class UserForbiddenException extends BaseForbiddenException {
    public UserForbiddenException(String message) {
        super("USER_UNAUTHORIZED", message);
    }
}
