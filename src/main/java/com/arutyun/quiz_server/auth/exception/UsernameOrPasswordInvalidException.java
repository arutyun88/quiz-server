package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;

public class UsernameOrPasswordInvalidException  extends BaseUnauthorizedException {
    public UsernameOrPasswordInvalidException(String message) {
        super("INVALID_USERNAME_OR_PASSWORD", message);
    }
}

