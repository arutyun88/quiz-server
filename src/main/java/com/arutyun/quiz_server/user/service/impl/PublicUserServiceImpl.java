package com.arutyun.quiz_server.user.service.impl;

import com.arutyun.quiz_server.user.data.entity.PublicUserEntity;
import com.arutyun.quiz_server.user.data.repository.PublicUserRepository;
import com.arutyun.quiz_server.user.exception.UserNotFoundException;
import com.arutyun.quiz_server.user.service.PublicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicUserServiceImpl implements PublicUserService {
    private final PublicUserRepository userRepository;

    @Override
    public PublicUserEntity findUser(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", id)));
    }
}
