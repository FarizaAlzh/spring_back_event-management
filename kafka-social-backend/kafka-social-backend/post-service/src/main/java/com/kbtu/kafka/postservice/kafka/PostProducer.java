package com.kbtu.kafka.postservice.kafka;

import com.kbtu.kafka.events.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostProducer {
    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;
    public void sendPostCreatedEvent(PostCreatedEvent event) {
        log.info("Sending PostCreatedEvent to Kafka for postId={}", event.getId());
        kafkaTemplate.send("posts", event.getId().toString(), event);
    }
}