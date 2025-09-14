package com.arutyun.quiz_server.gamification.service.model;

import java.util.UUID;

public record UserAchievementModel(
        UUID id,
        String name,
        String description,
        Integer points,
        String category) {

}
