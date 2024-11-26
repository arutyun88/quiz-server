package com.arutyun.quiz_server.auth.controller;

import com.arutyun.quiz_server.auth.converter.TokenDtoConverter;
import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.auth.dto.RequestLoginDto;
import com.arutyun.quiz_server.auth.dto.RequestRegisterDto;
import com.arutyun.quiz_server.auth.service.AuthService;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final TokenDtoConverter tokenDtoConverter;

    @PostMapping("/api/auth/login")
    public ResponseEntity<ResponseDto> authenticate(
            @RequestHeader("device_id") String deviceId,
            @Valid @RequestBody RequestLoginDto request
    ) {
        try {
            final UserEntity user = userService.findUser(
                    request.username(),
                    request.password()
            );

            final TokenEntity token = authService.generateToken(
                    user,
                    deviceId
            );

            return ResponseWrapper.ok(token, tokenDtoConverter);
        } catch (BaseException exception) {
            return ResponseWrapper.error(exception);
        }
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDto> register(
            @RequestHeader("device_id") String deviceId,
            @Valid @RequestBody RequestRegisterDto request
    ) {
        try {
            final UserEntity user = userService.createUser(
                    request.username(),
                    request.password(),
                    request.email()
            );

            final TokenEntity token = authService.generateToken(
                    user,
                    deviceId
            );

            return ResponseWrapper.ok(token, tokenDtoConverter);
        } catch (BaseException exception) {
            return ResponseWrapper.error(exception);
        }
    }
}
