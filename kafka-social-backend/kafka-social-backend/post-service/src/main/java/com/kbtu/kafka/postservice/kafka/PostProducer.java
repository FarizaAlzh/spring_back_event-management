package com.kbtu.kafka.postservice.kafka;

import com.kbtu.kafka.events.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostProducer {
    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;
    @Value("${app.kafka.enabled:false}")
    private boolean kafkaEnabled;
    @Value("${app.kafka.topics.posts:posts}")
    private String postsTopic;

    public void sendPostCreatedEvent(PostCreatedEvent event) {
        if (!kafkaEnabled) {
            log.info("Kafka publishing is disabled, skipping PostCreatedEvent for postId={}", event.postId());
            return;
        }

        log.info("Sending PostCreatedEvent to Kafka for postId={}", event.postId());
        kafkaTemplate.send(postsTopic, event.postId().toString(), event);
    }
}
