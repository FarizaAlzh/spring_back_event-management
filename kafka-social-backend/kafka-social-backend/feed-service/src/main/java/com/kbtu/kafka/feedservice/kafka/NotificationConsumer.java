package com.kbtu.kafka.feedservice.kafka;

import com.kbtu.kafka.events.PostCreatedEvent;
import com.kbtu.kafka.feedservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${app.kafka.topics.posts}",
            groupId = "${app.kafka.groups.notifications}",
            containerFactory = "postCreatedEventKafkaListenerContainerFactory"
    )
    public void consume(PostCreatedEvent event) {
        if (event.content() == null || event.content().isBlank()) {
            log.warn("Skipping notification for event {} because content is empty", event.postId());
            return;
        }

        notificationService.createNotification(event);
        log.info("🔔 Sending push notification to followers of user {} — new post {}",
                event.userId(), event.postId());
    }
}
