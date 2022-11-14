package ru.practicum.explorewithme.authAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.request.dto.RequestDto;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    @GetMapping("/{userId}/events")
    public List<EventFullDto> getAllEventsByUserId(@PathVariable long userId,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        return authorizeService.getAllEventsByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEventByInitiator(@PathVariable long userId, @RequestBody EventDto eventDto) {
        return authorizeService.updateEventByInitiator(userId, eventDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEventByInitiator(@PathVariable long userId, @RequestBody EventDto eventDto) {
        return authorizeService.addEventByInitiator(userId, eventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventInfoByUserId(@PathVariable long userId, @PathVariable long eventId) {
        return authorizeService.getEventInfoByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEventByInitiator(@PathVariable long userId, @PathVariable long eventId) {
        return authorizeService.cancelEventByInitiator(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserRequestsForThisEvent(@PathVariable long userId, @PathVariable long eventId) {
        return authorizeService.getUserRequestsForThisEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmOtherRequestForInitiatorEvent(@PathVariable long userId,
                                                           @PathVariable long eventId,
                                                           @PathVariable long reqId) {
        return authorizeService.confirmOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectOtherRequestForInitiatorEvent(@PathVariable long userId,
                                                          @PathVariable long eventId,
                                                          @PathVariable long reqId) {
        return authorizeService.rejectOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getUserRequestsForAllEvents(@PathVariable long userId) {
        return authorizeService.getUserRequestsForAllEvents(userId);
    }

    @PostMapping("/{userId}/requests")
    public RequestDto addUserRequestForThisEvent(@PathVariable long userId,
                                                 @RequestParam long eventId) {
        return authorizeService.addUserRequestForThisEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto rejectUserRequestForThisEvent(@PathVariable long userId,
                                                    @PathVariable long requestId) {
        return authorizeService.rejectUserRequestForThisEvent(userId, requestId);
    }
}
