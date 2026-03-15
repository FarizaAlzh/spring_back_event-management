package com.kbtu.kafka.postservice.dto.response;

import com.kbtu.kafka.postservice.model.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Response returned for a post")
public class PostResponse {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(example = "u1")
    private String userId;

    @Schema(example = "New hackathon announced for KBTU students")
    private String content;

    @Schema(example = "#kbtu #hackathon")
    private String hashtags;

    @Schema(example = "PUBLISHED")
    private PostStatus status;

    @Schema(example = "2026-03-15T12:00:00")
    private LocalDateTime createdAt;
}