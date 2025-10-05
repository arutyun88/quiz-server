package com.arutyun.quiz_server.auth.security.service;

import com.arutyun.quiz_server.auth.exception.UserUnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
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

    @Value("${jwt.access-expiration-in-ms}")
    private long ACCESS_TOKEN_VALIDITY;



    private static final String TOKEN_TYPE = "Bearer ";

    public String parseTokenByType(String token) {
        if (token != null && token.startsWith(TOKEN_TYPE)) {
            return token.substring(TOKEN_TYPE.length());
        }
        return null;
    }

    public String extractEmail(String token) throws UserUnauthorizedException {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                        .toList());
        claims.put("token_type", "access_token");
        return generateToken(claims, userDetails, ACCESS_TOKEN_VALIDITY);
    }

    private String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails,
            long tokenValidity
    ) {
        final long now = System.currentTimeMillis();
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + tokenValidity))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws UserUnauthorizedException {
        final String email = extractEmail(token);
        final boolean isAccessToken = extractClaim(
                token,
                claims -> claims.get("token_type").toString().equals("access_token")
        );
        return email.equals(userDetails.getUsername()) && isAccessToken;
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