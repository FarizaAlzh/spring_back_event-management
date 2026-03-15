package com.kbtu.kafka.feedservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Feed item response")
public record FeedItemResponse(
        @Schema(description = "Feed item identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Post identifier", example = "660e8400-e29b-41d4-a716-446655440000")
        UUID postId,
        @Schema(description = "User identifier", example = "u1")
        String userId,
        @Schema(description = "Post content", example = "Hello Kafka world!")
        String content,
        @Schema(description = "List of hashtags without #", example = "[\"kafka\", \"spring\"]")
        List<String> hashtags,
        @Schema(description = "Time when the post event was created", example = "2026-03-15T12:30:00")
        LocalDateTime createdAt
) {
}
