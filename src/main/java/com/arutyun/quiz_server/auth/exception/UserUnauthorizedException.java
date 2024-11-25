package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;

public class UserUnauthorizedException extends BaseUnauthorizedException {
    public UserUnauthorizedException(String message) {
        super("USER_UNAUTHORIZED", message);
    }
}
