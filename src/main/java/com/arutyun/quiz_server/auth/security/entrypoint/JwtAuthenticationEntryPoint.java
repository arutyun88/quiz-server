package com.arutyun.quiz_server.auth.security.entrypoint;

import com.arutyun.quiz_server.auth.exception.UserUnauthorizedException;
import com.arutyun.quiz_server.auth.exception.UsernameOrPasswordInvalidException;
import com.arutyun.quiz_server.auth.exception.WrappedAuthenticationException;
import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (authException.getCause() instanceof ConstraintViolationException) {
            throw authException;
        }

        final BaseException exception;
        if (authException instanceof WrappedAuthenticationException wrappedAuthenticationException) {
            exception = wrappedAuthenticationException.getException();
        } else if (authException instanceof BadCredentialsException) {
            exception = new UsernameOrPasswordInvalidException(authException.getMessage());
        } else {
            exception = new UserUnauthorizedException("User is not authorized");
        }

        final ResponseEntity<ResponseDto> responseException = ResponseWrapper.error(exception);
        response.setContentType("application/json");
        response.setStatus(responseException.getStatusCode().value());

        final String jsonResponse = objectMapper.writeValueAsString(responseException.getBody());

        response.getWriter().write(jsonResponse);
    }
}
