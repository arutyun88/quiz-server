package com.arutyun.quiz_server.user.controller;

import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.user.converter.UserDtoConverter;
import com.arutyun.quiz_server.user.data.entity.PublicUserEntity;
import com.arutyun.quiz_server.user.service.PublicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    final PublicUserService userService;
    final UserDtoConverter userDtoConverter;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<ResponseDto> findUser(
            @PathVariable("id") String userId
    ) throws BaseException {
        final PublicUserEntity user = userService.findUser(UUID.fromString(userId));

        return ResponseWrapper.ok(user, userDtoConverter);
    }
}
