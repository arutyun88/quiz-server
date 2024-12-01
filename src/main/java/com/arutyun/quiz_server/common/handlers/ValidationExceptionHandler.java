package com.arutyun.quiz_server.common.handlers;

import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.impl.CommonBadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

        StringBuilder errors = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append("; ");
        }

        return ResponseWrapper.error(
                new CommonBadRequestException(
                        errors.delete(errors.length() - 2, errors.length()).toString()
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(ConstraintViolationException ex) {

        StringBuilder errors = new StringBuilder();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.append(violation.getPropertyPath())
                    .append(" - ").append(violation.getMessage())
                    .append("; ");
        }

        return ResponseWrapper.error(
                new CommonBadRequestException(
                        errors.delete(errors.length() - 2, errors.length()).toString()
                )
        );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MissingRequestHeaderException ex) {

        String errors = String.format("%s - %s", ex.getHeaderName(), ex.getMessage());

        return ResponseWrapper.error(
                new CommonBadRequestException(
                        errors
                )
        );
    }
}