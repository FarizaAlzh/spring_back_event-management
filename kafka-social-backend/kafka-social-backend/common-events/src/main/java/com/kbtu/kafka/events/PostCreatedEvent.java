package com.kbtu.kafka.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostCreatedEvent(
        UUID postId,
        String userId,
        String content,
        List<String> hashtags,
        LocalDateTime timestamp
) {
}
