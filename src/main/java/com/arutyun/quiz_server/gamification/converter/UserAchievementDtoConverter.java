package com.arutyun.quiz_server.gamification.converter;

import com.arutyun.quiz_server.gamification.dto.UserAchievementDto;
import com.arutyun.quiz_server.gamification.service.model.UserAchievementModel;

import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Converter for transforming UserAchievementModel to UserAchievementDto.
 * <p>
 * Converts service layer model data to DTO format for client communication.
 * Maps achievement data from internal model structure to standardized DTO format.
 */
@Component
public class UserAchievementDtoConverter implements Function<UserAchievementModel, UserAchievementDto> {
    @Override
    public UserAchievementDto apply(UserAchievementModel userAchievement) {
        return new UserAchievementDto(
                userAchievement.id(),
                userAchievement.name(),
                userAchievement.description(),
                userAchievement.points(),
                userAchievement.category()
        );
    }
}
