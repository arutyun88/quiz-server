package com.arutyun.quiz_server.auth.service.impl;

import com.arutyun.quiz_server.auth.data.entity.RoleEntity;
import com.arutyun.quiz_server.auth.data.repository.RoleRepository;
import com.arutyun.quiz_server.auth.exception.UserAlreadyExistException;
import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.auth.data.repository.UserRepository;
import com.arutyun.quiz_server.auth.exception.UserCreateUnknownException;
import com.arutyun.quiz_server.auth.exception.UsernameOrPasswordInvalidException;
import com.arutyun.quiz_server.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findUser(
            String username,
            String password
    ) throws UsernameOrPasswordInvalidException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameOrPasswordInvalidException("Invalid username or password"));
    }

    @Override
    public UserEntity createUser(
            String username,
            String password,
            String email
    ) throws UserAlreadyExistException, UserCreateUnknownException {
        try {
            final Optional<RoleEntity> roleData = roleRepository.getByName("ROLE_USER");
            if (roleData.isEmpty()) {
                throw new UserCreateUnknownException("ROLE NOT FOUND");
            }

            return userRepository.save(
                    new UserEntity(
                            username,
                            passwordEncoder.encode(password),
                            email,
                            new HashSet<>(Collections.singleton(roleData.get()))
                    )
            );
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException exception) {
                throw new UserAlreadyExistException(
                        exception.getCause().getMessage()
                );
            } else {
                throw new UserAlreadyExistException(
                        e.getMostSpecificCause().getMessage()
                );
            }
        } catch (Exception e) {
            throw new UserCreateUnknownException(
                    String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage())
            );
        }
    }

}
