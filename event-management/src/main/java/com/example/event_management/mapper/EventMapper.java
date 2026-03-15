package com.example.event_management.mapper;

import com.example.event_management.dto.request.CreateEventRequest;
import com.example.event_management.dto.request.UpdateEventRequest;
import com.example.event_management.dto.response.EventResponse;
import com.example.event_management.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(CreateEventRequest request);
    EventResponse toResponse(Event event);
    void updateEventFromDto(UpdateEventRequest request, @MappingTarget Event event);
}