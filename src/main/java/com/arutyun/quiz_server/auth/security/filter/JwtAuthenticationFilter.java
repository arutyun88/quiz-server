package com.arutyun.quiz_server.auth.security.filter;

import com.arutyun.quiz_server.auth.exception.WrappedAuthenticationException;
import com.arutyun.quiz_server.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.arutyun.quiz_server.auth.security.service.JwtService;
import com.arutyun.quiz_server.auth.security.service.TokenService;
import com.arutyun.quiz_server.common.exception.BaseUnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (request.getRequestURI().startsWith("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwtToken = jwtService.parseTokenByType(authHeader);
            tokenService.tokenIsPresented(jwtToken);

            if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final String email = jwtService.extractEmail(jwtToken);

                if (email != null) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if (jwtService.isTokenValid(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);

        } catch (BaseUnauthorizedException exception) {
            SecurityContextHolder.clearContext();
            jwtAuthenticationEntryPoint.commence(request, response, new WrappedAuthenticationException(exception));
        }
    }
}
