package com.arutyun.quiz_server.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseException extends Exception {
    private final int code;
    private final String error;
    private final String message;
}
