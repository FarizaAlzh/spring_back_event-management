package com.kbtu.kafka.feedservice.service;

import com.kbtu.kafka.events.PostCreatedEvent;
import com.kbtu.kafka.feedservice.entity.FeedItem;
import com.kbtu.kafka.feedservice.repository.FeedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedCommandService {
    private final FeedItemRepository feedItemRepository;

    public void addToFeed(PostCreatedEvent event) {
        FeedItem feedItem = new FeedItem();
        feedItem.setId(UUID.randomUUID());
        feedItem.setPostId(event.postId());
        feedItem.setUserId(event.userId());
        feedItem.setContent(event.content());
        feedItem.setHashtags(event.hashtags());
        feedItem.setCreatedAt(event.timestamp());
        feedItemRepository.save(feedItem);
    }
}
