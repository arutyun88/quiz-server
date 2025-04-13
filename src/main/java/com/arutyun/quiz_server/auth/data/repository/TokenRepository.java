package com.arutyun.quiz_server.auth.data.repository;

import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    void deleteByUserAndDeviceId(UserEntity user, String deviceId);

    Optional<TokenEntity> findByAccessToken(String accessToken);

    Optional<TokenEntity> findByRefreshToken(String refreshToken);
}
