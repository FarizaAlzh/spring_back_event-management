package com.example.event_management.entity;

import com.example.event_management.model.enums.EventCategory;
import com.example.event_management.model.enums.EventFormat;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@Hidden
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "organizer_email", nullable = false)
    private String organizerEmail;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(nullable = false)
    private String location;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    private EventFormat format;
    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;
}