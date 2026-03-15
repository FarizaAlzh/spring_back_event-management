package com.example.event_management.exception;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response returned by the API")
public class ErrorResponse {

    @Schema(description = "Time when the error happened", example = "2026-03-15T20:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;
    @Schema(description = "HTTP status name", example = "Bad Request")
    private String error;

    @Schema(description = "Human-readable error message", example = "Validation failed")
    private String message;

    @Schema(description = "Request path that caused the error", example = "/api/v1/events")
    private String path;
    @Schema(description = "Field validation details when request validation fails")
    private Map<String, String> validationErrors;
}
