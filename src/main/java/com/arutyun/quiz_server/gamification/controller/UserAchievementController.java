package com.arutyun.quiz_server.gamification.controller;

import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserAchievementController {

    /**
     * Get achievements for current authenticated user.
     * Includes all achievements (unlocked and locked) with progress info.
     * <hr>
     * Получить достижения для текущего аутентифицированного пользователя.
     * Включает все достижения (разблокированные и заблокированные) с информацией о прогрессе.
     */
    @GetMapping("achievements")
    public ResponseEntity<ResponseDto> getCurrentUserAchievements(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestHeader(value = "X-Lang") String language
    ) throws BaseException {
        return ResponseWrapper.ok(
                "",
                value -> "Achievements fetched: " + user.getId() + " " + user.getEmail()
        );
    }

    /**
     * Get unlocked achievements for specific user.
     * Only returns achievements that the user has unlocked.
     * <hr>
     * Получить разблокированные достижения для конкретного пользователя.
     * Возвращает только достижения, которые пользователь разблокировал.
     */
    @GetMapping("{userId}/achievements")
    public ResponseEntity<ResponseDto> getUserAchievements(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestHeader(value = "X-Lang") String language
    ) throws BaseException {
        return ResponseWrapper.ok("", value -> "Achievements fetched " + userId);
    }
}
