package com.kbtu.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {
    private UUID id;
    private String userId;
    private String content;
    private String hashtags;
    private String status;
    private LocalDateTime createdAt;
}