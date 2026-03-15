package com.kbtu.kafka.postservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request for creating a post")
public class CreatePostRequest {

    @Schema(example = "u1", description = "User identifier")
    @NotBlank
    private String userId;

    @Schema(example = "New hackathon announced for KBTU students", description = "Post content")
    @NotBlank
    @Size(max = 280)
    private String content;

    @Schema(example = "#kbtu #hackathon", description = "Optional hashtags")
    private String hashtags;
}