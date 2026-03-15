package com.kbtu.kafka.postservice.mapper;

import com.kbtu.kafka.postservice.dto.response.PostResponse;
import com.kbtu.kafka.postservice.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "timestamp", source = "createdAt")
    PostResponse toResponse(Post post);
}
