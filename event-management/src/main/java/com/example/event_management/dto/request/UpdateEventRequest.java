package com.example.event_management.dto.request;

import com.example.event_management.model.enums.EventCategory;
import com.example.event_management.model.enums.EventFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Request for updating an existing event")
public class UpdateEventRequest {

    @Schema(description = "Event title", example = "Advanced Spring Boot Workshop")
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;
    @Schema(description = "Organizer email", example = "backend-club@kbtu.kz")
    @NotBlank
    @Email
    private String organizerEmail;
    @Schema(description = "Date and time of the event", example = "2026-05-10T17:30:00")
    @NotNull
    @Future
    private LocalDateTime eventDate;

    @Schema(description = "Location of the event", example = "KBTU Hall 401")
    @NotBlank
    @Size(min = 2, max = 120)
    private String location;
    @Schema(description = "Detailed event description", example = "Updated agenda for the backend workshop")
    @Size(max = 1000)
    private String description;

    @Schema(description = "Category of the event", example = "LECTURE")
    @NotNull
    private EventCategory category;

    @Schema(description = "Format of the event", example = "ONLINE")
    @NotNull
    private EventFormat format;
    @Schema(description = "Maximum number of participants", example = "80")
    @NotNull
    @Min(1)
    private Integer maxParticipants;
}
