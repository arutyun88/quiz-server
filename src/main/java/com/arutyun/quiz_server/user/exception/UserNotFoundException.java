package com.arutyun.quiz_server.user.exception;

import com.arutyun.quiz_server.common.exception.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
    public UserNotFoundException(String message) {
        super("USER_NOT_FOUND", message);
    }
}
