package com.kbtu.kafka.feedservice.controller;

import com.kbtu.kafka.feedservice.dto.FeedItemResponse;
import com.kbtu.kafka.feedservice.service.FeedQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
@Tag(name = "Feed API", description = "Operations for reading user feed items")
public class FeedController {
    private final FeedQueryService feedQueryService;

    @Operation(summary = "Get feed items by user", description = "Returns all feed items stored for a user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Feed returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FeedItemResponse.class)))
            )
    })
    @GetMapping
    public List<FeedItemResponse> getFeed(
            @Parameter(description = "User identifier whose feed should be returned", example = "u1")
            @RequestParam String userId
    ) {
        return feedQueryService.getFeedByUserId(userId);
    }
}
