package com.arutyun.quiz_server.auth.service.impl;

import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.auth.data.repository.TokenRepository;
import com.arutyun.quiz_server.auth.exception.TokenNotFoundException;
import com.arutyun.quiz_server.auth.exception.UserUnauthorizedException;
import com.arutyun.quiz_server.auth.security.service.JwtService;
import com.arutyun.quiz_server.auth.service.AuthService;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.auth.exception.UserTokenCreateException;
import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public TokenEntity generateToken(
            UserEntity user,
            String deviceId
    ) throws BaseException {
        try {
            tokenRepository.deleteByUserAndDeviceId(user, deviceId);
            tokenRepository.flush();

            final TokenEntity token = new TokenEntity(
                    jwtService.generateAccessToken(user),
                    jwtService.generateRefreshToken(user),
                    deviceId,
                    user
            );

            return tokenRepository.save(token);
        } catch (HibernateException exception) {
            throw new UserTokenCreateException("Tokens is not created");
        }
    }

    @Override
    public TokenEntity fetchTokenByRefresh(String deviceId, String refreshToken) throws BaseUnauthorizedException {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new UserUnauthorizedException("Token is expired");
        }

        final TokenEntity token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new TokenNotFoundException("Token already refreshed")
        );

        if (!token.getDeviceId().equals(deviceId)) {
            throw new TokenNotFoundException("Token already refreshed");
        }

        return token;
    }

    public void logoutUserByToken(String token) {
        final Optional<TokenEntity> storedToken = tokenRepository.findByAccessToken(jwtService.parseTokenByType(token));
        storedToken.ifPresent(presentedToken -> {
            tokenRepository.delete(presentedToken);
            SecurityContextHolder.clearContext();
        });
    }
}
