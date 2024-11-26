package com.arutyun.quiz_server.common.exception.impl;

import com.arutyun.quiz_server.common.exception.BaseBadRequestException;

public class CommonBadRequestException extends BaseBadRequestException {
    public CommonBadRequestException(String message) {
        super("COMMON_BAD_REQUEST", message);
    }
}