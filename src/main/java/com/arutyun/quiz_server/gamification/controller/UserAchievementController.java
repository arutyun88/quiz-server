package com.arutyun.quiz_server.gamification.controller;

import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.model.Meta;
import com.arutyun.quiz_server.gamification.converter.UserAchievementDtoConverter;
import com.arutyun.quiz_server.gamification.service.UserAchievementService;
import com.arutyun.quiz_server.gamification.service.model.UserAchievementModel;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for user achievements.
 * <p>
 * Provides endpoints for retrieving user achievements with localization support.
 * Handles both current user and specific user achievement requests.
 * <p>
 * All endpoints support localization through X-Lang header.
 * <p>
 * Base path: /api/user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserAchievementController {

    private final UserAchievementService userAchievementService;
    private final UserAchievementDtoConverter userAchievementDtoConverter;

    /**
     * Get unlocked achievements for current authenticated user.
     * <p>
     * Returns only achievements that the user has unlocked.
     * Includes localization and achievement details.
     * <p>
     * Sorting: by unlock date DESC (newest unlocked achievements first)
     */
    @GetMapping("achievements")
    public ResponseEntity<ResponseDto> getCurrentUserAchievements(
            @AuthenticationPrincipal UserEntity user,
            @RequestHeader(value = "X-Lang", defaultValue = "en") String language
    ) {
        List<UserAchievementModel> achievements = userAchievementService.getUserAllAchievements(user.getId(), language);

        return ResponseWrapper.ok(
                achievements, 
                userAchievementDtoConverter,
                new Meta(achievements.size(), 0, achievements.size())
        );
    }

    /**
     * Get unlocked achievements for specific user.
     * <p>
     * Returns only achievements that the specified user has unlocked.
     * Used for displaying other users' achievements.
     * <p>
     * Sorting: by unlock date DESC (newest unlocked achievements first)
     */
    @GetMapping("{userId}/achievements")
    public ResponseEntity<ResponseDto> getUserAchievements(
            @PathVariable UUID userId,
            @RequestHeader(value = "X-Lang", defaultValue = "en") String language
    ) {
        List<UserAchievementModel> achievements = 
            userAchievementService.getUserAllAchievements(userId, language);

        return ResponseWrapper.ok(
                achievements, 
                userAchievementDtoConverter,
                new Meta(achievements.size(), 0, achievements.size())
        );
    }
}
