package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;

public class UserTokenCreateException  extends BaseUnauthorizedException {
    public UserTokenCreateException(String message) {
        super("USER_TOKENS_CREATE_FAILED", message);
    }
}