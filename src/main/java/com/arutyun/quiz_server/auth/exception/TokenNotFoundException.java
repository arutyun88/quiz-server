package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;

public class TokenNotFoundException extends BaseUnauthorizedException {
    public TokenNotFoundException(String message) {
        super("TOKEN_ALREADY_UPDATED", message);
    }
}
