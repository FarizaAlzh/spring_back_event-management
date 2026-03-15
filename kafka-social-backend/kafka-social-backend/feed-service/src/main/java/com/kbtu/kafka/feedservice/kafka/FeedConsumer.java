package com.kbtu.kafka.feedservice.kafka;

import com.kbtu.kafka.events.PostCreatedEvent;
import com.kbtu.kafka.feedservice.service.FeedCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeedConsumer {
    private final FeedCommandService feedCommandService;

    @KafkaListener(
            topics = "${app.kafka.topics.posts}",
            groupId = "${app.kafka.groups.feed}",
            containerFactory = "postCreatedEventKafkaListenerContainerFactory"
    )
    public void consume(PostCreatedEvent event) {
        if (event.content() == null || event.content().isBlank()) {
            log.warn("⚠ Skipping event {} — content is empty", event.postId());
            return;
        }

        feedCommandService.addToFeed(event);
        log.info("📰 Adding post {} by user {} to follower feeds — '{}'",
                event.postId(), event.userId(), event.content());
    }
}
