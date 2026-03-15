package com.kbtu.kafka.postservice.repository;

import com.kbtu.kafka.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}