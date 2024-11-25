package com.arutyun.quiz_server.auth.exception;

import com.arutyun.quiz_server.common.exception.BaseException;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class WrappedAuthenticationException extends AuthenticationException {
    private final BaseException exception;

    public WrappedAuthenticationException(BaseException exception) {
        super(exception.getMessage());
        this.exception = exception;
    }
}
