package com.kbtu.kafka.feedservice.service;

import com.kbtu.kafka.events.PostCreatedEvent;
import com.kbtu.kafka.feedservice.entity.Notification;
import com.kbtu.kafka.feedservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createNotification(PostCreatedEvent event) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setPostId(event.postId());
        notification.setUserId(event.userId());
        notification.setMessage("New post from user " + event.userId() + ": " + event.postId());
        notification.setCreatedAt(event.timestamp());
        notificationRepository.save(notification);
    }
}
