package com.arutyun.quiz_server.user.service;

import com.arutyun.quiz_server.auth.exception.*;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.user.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.UUID;

public interface UserService {
    UserEntity findUser(
            String email,
            String password
    ) throws UsernameOrPasswordInvalidException;

    UserEntity createUser(
            String password,
            String email
    ) throws UserAlreadyExistException, UserCreateUnknownException;

    UserEntity getCurrentUser() throws UserNotFoundException;

    UserEntity getUserById(UUID id) throws UserNotFoundException;

    UserEntity updateCurrentUser(String name, LocalDate birthDate) throws UserNotFoundException;
}
