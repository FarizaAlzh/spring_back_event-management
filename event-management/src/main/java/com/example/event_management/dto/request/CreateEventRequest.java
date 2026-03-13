package com.example.event_management.dto.request;

import com.example.event_management.model.enums.EventCategory;
import com.example.event_management.model.enums.EventFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Request for creating an event")
public class CreateEventRequest {

    @Schema(description = "Event title", example = "Spring Boot Workshop")
    @NotBlank
    private String title;

    @Schema(description = "Organizer email", example = "events@kbtu.kz")
    @NotBlank
    @Email
    private String organizerEmail;

    @Schema(description = "Date and time of the event", example = "2026-04-20T15:00:00")
    @NotNull
    private LocalDateTime eventDate;

    @Schema(description = "Location of the event", example = "KBTU Hall 305")
    @NotBlank
    private String location;

    @Schema(description = "Detailed event description", example = "Hands-on backend workshop")
    private String description;

    @Schema(description = "Category of the event", example = "WORKSHOP")
    @NotNull
    private EventCategory category;

    @Schema(description = "Format of the event", example = "OFFLINE")
    @NotNull
    private EventFormat format;

    @Schema(description = "Maximum number of participants", example = "40")
    @NotNull
    @Min(1)
    private Integer maxParticipants;
}