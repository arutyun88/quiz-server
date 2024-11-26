package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseConflictException;

public class UserAlreadyExistException extends BaseConflictException {
    public UserAlreadyExistException(String message) {
        super("USER_ALREADY_EXIST", message);
    }
}