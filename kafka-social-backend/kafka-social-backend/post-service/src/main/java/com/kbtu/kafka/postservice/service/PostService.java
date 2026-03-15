package com.kbtu.kafka.postservice.service;

import com.kbtu.kafka.events.PostCreatedEvent;
import com.kbtu.kafka.postservice.dto.request.CreatePostRequest;
import com.kbtu.kafka.postservice.dto.response.PostResponse;
import com.kbtu.kafka.postservice.entity.Post;
import com.kbtu.kafka.postservice.exception.ResourceNotFoundException;
import com.kbtu.kafka.postservice.kafka.PostProducer;
import com.kbtu.kafka.postservice.mapper.PostMapper;
import com.kbtu.kafka.postservice.model.enums.PostStatus;
import com.kbtu.kafka.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostProducer postProducer;
    public PostResponse createPost(CreatePostRequest request) {
        log.info("Creating post for userId={}", request.getUserId());

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        post.setHashtags(request.getHashtags());
        post.setStatus(PostStatus.PUBLISHED);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        PostCreatedEvent event = new PostCreatedEvent(
                savedPost.getId(),
                savedPost.getUserId(),
                savedPost.getContent(),
                savedPost.getHashtags(),
                savedPost.getCreatedAt()
        );

        postProducer.sendPostCreatedEvent(event);

        log.info("Post saved and event sent, postId={}", savedPost.getId());

        return postMapper.toResponse(savedPost);
    }

    public PostResponse getPostById(UUID postId) {
        log.info("Fetching post by id={}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return postMapper.toResponse(post);
    }
}
