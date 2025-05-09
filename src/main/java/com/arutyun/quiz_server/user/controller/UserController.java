package com.arutyun.quiz_server.user.controller;

import com.arutyun.quiz_server.question.service.StatisticsService;
import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.converter.UserStatisticsDtoConverter;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.user.dto.UpdatePasswordRequestDto;
import com.arutyun.quiz_server.user.dto.UpdateUserRequestDto;
import com.arutyun.quiz_server.user.service.UserService;
import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.user.converter.UserDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    final UserService userService;
    final StatisticsService statisticsService;
    final UserDtoConverter userDtoConverter;
    final UserStatisticsDtoConverter userStatisticsDtoConverter;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<ResponseDto> findUser(
            @PathVariable("id") String userId
    ) throws BaseException {
        final UserEntity user = userService.getUserById(UUID.fromString(userId));

        return ResponseWrapper.ok(user, userDtoConverter);
    }

    @GetMapping("api/user")
    public ResponseEntity<ResponseDto> findUser() throws BaseException {
        final UserEntity user = userService.getCurrentUser();

        return ResponseWrapper.ok(user, userDtoConverter);
    }

    @PostMapping("api/user")
    public ResponseEntity<ResponseDto> updateUser(
            @RequestBody UpdateUserRequestDto request
    ) throws BaseException {
        final UserEntity user = userService.updateCurrentUser(
                request.name(),
                request.birthDate()
        );

        return ResponseWrapper.ok(user, userDtoConverter);
    }

    @PostMapping("api/user/password")
    public ResponseEntity<ResponseDto> updatePassword(
            @RequestBody UpdatePasswordRequestDto request
    ) throws BaseException {
        userService.updatePassword(
                request.oldPassword(),
                request.newPassword()
        );

        return ResponseWrapper.ok("", value -> "Password changed");
    }

    @GetMapping("api/user/statistics")
    public ResponseEntity<ResponseDto> getUserStatistics() throws BaseException {
        final UserEntity user = userService.getCurrentUser();
        final UserStatistics statistics = statisticsService.fetch(user);

        return ResponseWrapper.ok(statistics, userStatisticsDtoConverter);
    }
}
