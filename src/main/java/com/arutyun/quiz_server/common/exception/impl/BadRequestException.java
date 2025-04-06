package com.arutyun.quiz_server.common.exception.impl;

import com.arutyun.quiz_server.common.exception.BaseBadRequestException;

public class BadRequestException extends BaseBadRequestException {
    public BadRequestException(String message) {
        super("BAD_REQUEST", message);
    }
}