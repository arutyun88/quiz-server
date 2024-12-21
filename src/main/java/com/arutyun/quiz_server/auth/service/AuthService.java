package com.arutyun.quiz_server.auth.service;

import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.auth.data.entity.UserEntity;

public interface AuthService {
    TokenEntity generateToken(UserEntity user, String deviceId) throws BaseException;

    TokenEntity fetchTokenByRefresh(String deviceId, String token) throws BaseException;

    void logoutUserByToken(String token);
}
