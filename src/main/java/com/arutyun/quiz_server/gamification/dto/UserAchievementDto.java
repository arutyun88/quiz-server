package com.arutyun.quiz_server.gamification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data Transfer Object for user achievement information.
 * <p>
 * Represents achievement data with localization support for client communication.
 * Contains basic achievement information: ID, name, description, points, and category.
 */
public record UserAchievementDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("points") Integer points,
                @JsonProperty("category") String category) {
}