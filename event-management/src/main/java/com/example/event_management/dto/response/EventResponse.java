package com.example.event_management.dto.response;

import com.example.event_management.model.enums.EventCategory;
import com.example.event_management.model.enums.EventFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Response returned for an event")
public class EventResponse {

    @Schema(description = "Event id", example = "1")
    private Long id;
    @Schema(description = "Event title", example = "Spring Boot Workshop")
    private String title;

    @Schema(description = "Organizer email", example = "events@kbtu.kz")
    private String organizerEmail;

    @Schema(description = "Date and time of the event", example = "2026-04-20T15:00:00")
    private LocalDateTime eventDate;

    @Schema(description = "Event location", example = "KBTU Hall 305")
    private String location;

    @Schema(description = "Event description", example = "Hands-on backend workshop")
    private String description;

    @Schema(description = "Event category", example = "WORKSHOP")
    private EventCategory category;

    @Schema(description = "Event format", example = "OFFLINE")
    private EventFormat format;

    @Schema(description = "Maximum number of participants", example = "40")
    private Integer maxParticipants;
}