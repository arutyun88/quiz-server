package com.arutyun.quiz_server.auth.security.service;

import com.arutyun.quiz_server.auth.exception.UserUnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-in-ms}")
    private long TOKEN_VALIDITY;

    private final static String TOKEN_TYPE = "Bearer ";

    public String parseTokenByType(String token) {
        if (token != null && token.startsWith(TOKEN_TYPE)) {
            return token.substring(TOKEN_TYPE.length());
        }
        return null;
    }

    public String extractUsername(String token) throws UserUnauthorizedException {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws UserUnauthorizedException {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws UserUnauthorizedException {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws UserUnauthorizedException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws UserUnauthorizedException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException exception) {
            String message;
            if (exception instanceof ExpiredJwtException) {
                message = "Token is expired";
            } else if (exception instanceof UnsupportedJwtException) {
                message = "Token is unsupported";
            } else if (exception instanceof MalformedJwtException) {
                message = "Token is malformed";
            } else {
                message = "Token unknown exception";
            }
            throw new UserUnauthorizedException(message);
        }
    }

    private SecretKey getSigningKey() {
        final byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
}