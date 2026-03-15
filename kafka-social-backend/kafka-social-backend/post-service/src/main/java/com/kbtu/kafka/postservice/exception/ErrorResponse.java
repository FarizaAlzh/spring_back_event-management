package com.kbtu.kafka.postservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Standard error response")
public class ErrorResponse {
    @Schema(description = "Time when the error happened", example = "2026-03-15T12:45:00")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "400")
    private int status;
    @Schema(description = "Error message", example = "content: must not be blank")
    private String error;
}
