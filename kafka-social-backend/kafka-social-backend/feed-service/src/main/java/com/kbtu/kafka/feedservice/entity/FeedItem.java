package com.kbtu.kafka.feedservice.entity;

import com.kbtu.kafka.feedservice.entity.converter.HashtagListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feed_items")
@Getter
@Setter
public class FeedItem {

    @Id
    private UUID id;

    @Column(name = "post_id", nullable = false, unique = true)
    private UUID postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Convert(converter = HashtagListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> hashtags;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
