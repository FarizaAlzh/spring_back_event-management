package com.example.event_management.controller;

import com.example.event_management.dto.request.CreateEventRequest;
import com.example.event_management.dto.request.UpdateEventRequest;
import com.example.event_management.dto.response.EventResponse;
import com.example.event_management.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.event_management.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Event API", description = "Operations for managing KBTU events")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Create event", description = "Creates a new event")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Event created successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = """
                                            {
                                              "timestamp": "2026-03-15T20:30:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Validation failed",
                                              "path": "/api/v1/events",
                                              "validationErrors": {
                                                "title": "size must be between 3 and 100",
                                                "organizerEmail": "must be a well-formed email address"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Server error",
                                    value = """
                                            {
                                              "timestamp": "2026-03-15T20:30:00",
                                              "status": 500,
                                              "error": "Internal Server Error",
                                              "message": "Unexpected internal server error",
                                              "path": "/api/v1/events"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(
            summary = "Get all events",
            description = "Returns paginated list of events. Use page, size and sort query parameters."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Events returned successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @ParameterObject
            @Parameter(description = "Pagination parameters: page, size and sort")
            Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }
    @Operation(summary = "Get event by id", description = "Returns one event by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Not found",
                                    value = """
                                            {
                                              "timestamp": "2026-03-15T20:30:00",
                                              "status": 404,
                                              "error": "Not Found",
                                              "message": "Event not found with id: 99",
                                              "path": "/api/v1/events/99"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    @Operation(summary = "Update event", description = "Updates an existing event")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Event updated successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }
    @Operation(summary = "Delete event", description = "Deletes an event by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
