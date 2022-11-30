package ru.practicum.explorewithme.adminAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.adminAPI.service.AdminEventService;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminEventController {

    private final AdminEventService administratorService;

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam(defaultValue = "", required = false) List<Long> users,
                                           @RequestParam(required = false) List<String> states,
                                           @RequestParam(defaultValue = "", required = false) List<Long> categories,
                                           @RequestParam(required = false) String rangeStart,
                                           @RequestParam(required = false) String rangeEnd,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size,
                                           HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting all events by administrator: " + request.getServletPath());
        return administratorService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable @Positive long eventId, @RequestBody @Valid EventDto eventDto, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Updating event by administrator: " + request.getServletPath());
        return administratorService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @Positive long eventId, HttpServletRequest request) throws EventStateException, EntityNotFoundException {
        log.info("Publish event by administrator: " + request.getServletPath());
        return administratorService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @Positive long eventId, HttpServletRequest request) throws EventStateException, EntityNotFoundException {
        log.info("Rejecting event by administrator: " + request.getServletPath());
        return administratorService.rejectEvent(eventId);
    }
}
