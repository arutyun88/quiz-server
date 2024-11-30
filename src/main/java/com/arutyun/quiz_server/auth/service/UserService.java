package com.arutyun.quiz_server.auth.service;

import com.arutyun.quiz_server.auth.exception.*;
import com.arutyun.quiz_server.auth.data.entity.UserEntity;

public interface UserService {
    UserEntity findUser(
            String username,
            String password
    ) throws UsernameOrPasswordInvalidException;

    UserEntity createUser(
            String username,
            String password,
            String email
    ) throws UserAlreadyExistException, UserCreateUnknownException;

    UserEntity getCurrentUser();
}
