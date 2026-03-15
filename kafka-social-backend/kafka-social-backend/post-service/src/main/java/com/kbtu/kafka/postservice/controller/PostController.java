package com.kbtu.kafka.postservice.controller;

import com.kbtu.kafka.postservice.dto.request.CreatePostRequest;
import com.kbtu.kafka.postservice.dto.response.PostResponse;
import com.kbtu.kafka.postservice.exception.ErrorResponse;
import com.kbtu.kafka.postservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "Operations for creating and retrieving posts")
public class PostController {
    private final PostService postService;
    @Operation(summary = "Publish a new post", description = "Creates a new post, stores it in PostgreSQL and publishes a Kafka event")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Post published successfully",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody(
                    description = "Payload for publishing a new post",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreatePostRequest.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody CreatePostRequest request
    ) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get post by id", description = "Returns one post by its UUID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Post found",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(
            @Parameter(description = "Unique post identifier", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId
    ) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }
}
