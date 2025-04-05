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
    ) throws BaseException {
        final UserEntity user = userService.findUser(
                request.email(),
                request.password()
        );

        final TokenEntity token = authService.generateToken(
                user,
                deviceId
        );

        return ResponseWrapper.ok(token, tokenDtoConverter);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDto> register(
            @RequestHeader("device_id") String deviceId,
            @Valid @RequestBody RequestRegisterDto request
    ) throws BaseException {
        final UserEntity user = userService.createUser(
                request.password(),
                request.email()
        );

        final TokenEntity token = authService.generateToken(
                user,
                deviceId
        );

        return ResponseWrapper.ok(token, tokenDtoConverter);
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<ResponseDto> refresh(
            @RequestHeader("device_id") String deviceId,
            @RequestParam String token
    ) throws BaseException {
        final TokenEntity oldToken = authService.fetchTokenByRefresh(deviceId, token);

        final TokenEntity newToken = authService.generateToken(
                oldToken.getUser(),
                deviceId
        );

        return ResponseWrapper.ok(newToken, tokenDtoConverter);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<ResponseDto> logout(
            @RequestHeader("Authorization") String token
    ) throws BaseException {
        authService.logoutUserByToken(token);
        return ResponseWrapper.ok(token, value -> "The user was unauthorized");
    }
}
