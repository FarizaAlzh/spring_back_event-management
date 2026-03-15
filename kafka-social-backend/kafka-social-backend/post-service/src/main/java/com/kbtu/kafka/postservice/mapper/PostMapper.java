package com.kbtu.kafka.postservice.mapper;

import com.kbtu.kafka.postservice.dto.response.PostResponse;
import com.kbtu.kafka.postservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toResponse(Post post);
}