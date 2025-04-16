package com.arutyun.quiz_server.user.service.impl;

import com.arutyun.quiz_server.user.data.entity.RoleEntity;
import com.arutyun.quiz_server.auth.data.repository.RoleRepository;
import com.arutyun.quiz_server.auth.exception.*;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.user.data.repository.UserRepository;
import com.arutyun.quiz_server.user.exception.UserUpdateException;
import com.arutyun.quiz_server.user.service.UserService;
import com.arutyun.quiz_server.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findUser(
            String email,
            String password
    ) throws UsernameOrPasswordInvalidException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameOrPasswordInvalidException("Invalid username or password"));
    }

    @Override
    public UserEntity createUser(
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
                            email,
                            passwordEncoder.encode(password),
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

    @Override
    public UserEntity getCurrentUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            final Object principal = authentication.getPrincipal();
            if (principal instanceof UserEntity user) {
                return user;
            }
        }

        throw new UserNotFoundException("User %s not found");
    }

    @Override
    public UserEntity getUserById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", id)));
    }

    @Override
    @Transactional
    public UserEntity updateCurrentUser(
            String name,
            LocalDate birthDate
    ) throws UserNotFoundException {
        final UserEntity user = getCurrentUser();

        if (name != null) {
            user.setName(name);
        }

        if (birthDate != null) {
            user.setBirthDate(birthDate);
        }

        return userRepository.save(user);
    }

    @Override
    public void updatePassword(
            String oldPassword,
            String newPassword
    ) throws UserNotFoundException, UserUpdateException {
        final UserEntity user = getCurrentUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserUpdateException("Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }
}
