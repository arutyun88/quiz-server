package com.arutyun.quiz_server.auth.security.service;

import com.arutyun.quiz_server.auth.data.repository.TokenRepository;
import com.arutyun.quiz_server.auth.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    final TokenRepository tokenRepository;

    public void tokenIsPresented(String token) throws TokenNotFoundException {
        tokenRepository.findByAccessToken(token).orElseThrow(
                () -> new TokenNotFoundException("Token not found")
        );
    }
}
