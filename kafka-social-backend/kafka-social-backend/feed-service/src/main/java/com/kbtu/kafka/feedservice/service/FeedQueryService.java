package com.kbtu.kafka.feedservice.service;

import com.kbtu.kafka.feedservice.dto.FeedItemResponse;
import com.kbtu.kafka.feedservice.repository.FeedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedQueryService {
    private final FeedItemRepository feedItemRepository;

    public List<FeedItemResponse> getFeedByUserId(String userId) {
        return feedItemRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(feedItem -> new FeedItemResponse(
                        feedItem.getId(),
                        feedItem.getPostId(),
                        feedItem.getUserId(),
                        feedItem.getContent(),
                        feedItem.getHashtags(),
                        feedItem.getCreatedAt()
                ))
                .toList();
    }
}
