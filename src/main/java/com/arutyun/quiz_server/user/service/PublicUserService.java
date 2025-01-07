package com.arutyun.quiz_server.user.service;

import com.arutyun.quiz_server.user.data.entity.PublicUserEntity;
import com.arutyun.quiz_server.user.exception.UserNotFoundException;

import java.util.UUID;

public interface PublicUserService {
    PublicUserEntity findUser(UUID id) throws UserNotFoundException;
}
