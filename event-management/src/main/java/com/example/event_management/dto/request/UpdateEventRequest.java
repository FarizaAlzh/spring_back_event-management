package com.example.event_management.dto.request;

import com.example.event_management.model.enums.EventCategory;
import com.example.event_management.model.enums.EventFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventRequest {

    @NotBlank
    private String title;
    @NotBlank
    @Email
    private String organizerEmail;
    @NotNull
    private LocalDateTime eventDate;
    @NotBlank
    private String location;
    private String description;
    @NotNull
    private EventCategory category;
    @NotNull
    private EventFormat format;
    @NotNull
    @Min(1)
    private Integer maxParticipants;
}