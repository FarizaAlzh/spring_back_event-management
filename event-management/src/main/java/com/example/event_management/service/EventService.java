package com.example.event_management.service;

import com.example.event_management.dto.request.CreateEventRequest;
import com.example.event_management.dto.request.UpdateEventRequest;
import com.example.event_management.dto.response.EventResponse;
import com.example.event_management.entity.Event;
import com.example.event_management.exception.ResourceNotFoundException;
import com.example.event_management.mapper.EventMapper;
import com.example.event_management.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventResponse createEvent(CreateEventRequest request) {
        log.info("Creating event with title: {}", request.getTitle());
        log.debug("Create request payload: organizerEmail={}, eventDate={}, category={}, format={}, maxParticipants={}",
                request.getOrganizerEmail(),
                request.getEventDate(),
                request.getCategory(),
                request.getFormat(),
                request.getMaxParticipants());

        Event event = eventMapper.toEntity(request);
        Event savedEvent = eventRepository.save(event);

        log.info("Event created with id: {}", savedEvent.getId());

        return eventMapper.toResponse(savedEvent);
    }

    public Page<EventResponse> getAllEvents(Pageable pageable) {
        log.info("Fetching events with pagination");
        log.debug("Page request details: page={}, size={}, sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        return eventRepository.findAll(pageable)
                .map(eventMapper::toResponse);
    }
    public EventResponse getEventById(Long id) {
        log.info("Fetching event with id: {}", id);
        log.debug("Looking up event in repository by id={}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        return eventMapper.toResponse(event);
    }
    public EventResponse updateEvent(Long id, UpdateEventRequest request) {
        log.info("Updating event with id: {}", id);
        log.debug("Update request payload for id={}: title={}, eventDate={}, location={}",
                id,
                request.getTitle(),
                request.getEventDate(),
                request.getLocation());

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        eventMapper.updateEventFromDto(request, event);
        Event updatedEvent = eventRepository.save(event);

        log.info("Event updated with id: {}", updatedEvent.getId());

        return eventMapper.toResponse(updatedEvent);
    }
    public void deleteEvent(Long id) {
        log.info("Deleting event with id: {}", id);
        log.debug("Checking existence before delete for event id={}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        eventRepository.delete(event);

        log.info("Event deleted with id: {}", id);
    }
}
