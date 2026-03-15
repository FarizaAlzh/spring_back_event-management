package com.kbtu.kafka.postservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Schema(description = "Request for creating a post")
public class CreatePostRequest {
    @Schema(example = "u1", description = "User identifier")
    @NotBlank(message = "must not be blank")
    private String userId;
    @Schema(example = "New hackathon announced for KBTU students", description = "Post content")
    @NotBlank(message = "must not be blank")
    @Size(max = 280, message = "must be at most 280 characters")
    private String content;
    @Schema(example = "[\"kafka\", \"spring\"]", description = "List of hashtags without #")
    private List<String> hashtags;
}
