package ru.practicum.explorewithme.authAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.request.dto.RequestFullDto;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    @GetMapping("/{userId}/events")
    public List<EventFullDto> getAllEventsByUserId(@PathVariable @Positive long userId,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                   @RequestParam(defaultValue = "10") @Positive Integer size) throws EntityNotFoundException {
        log.info("Getting all event by user id:{}", userId);
        return authorizeService.getAllEventsByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEventByInitiator(@PathVariable @Positive long userId, @RequestBody @Valid EventDto eventDto) throws EventStateException, EntityNotFoundException {
        log.info("Updating event by user id:{}", userId);
        return authorizeService.updateEventByInitiator(userId, eventDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEventByInitiator(@PathVariable @Positive long userId, @RequestBody @Valid EventDto eventDto) throws EntityNotFoundException, ValidationException {
        log.info("Adding event by user id:{}", userId);
        return authorizeService.addEventByInitiator(userId, eventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventInfoByUserId(@PathVariable @Positive long userId, @PathVariable @Positive long eventId) throws EntityNotFoundException {
        log.info("Getting event info by user id:{}", userId);
        return authorizeService.getEventInfoByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEventByInitiator(@PathVariable @Positive long userId, @PathVariable @Positive long eventId) throws EventStateException, EntityNotFoundException {
        log.info("Cancelling event by user id:{}", userId);
        return authorizeService.cancelEventByInitiator(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestFullDto> getUserRequestsForThisEvent(@PathVariable @Positive long userId, @PathVariable @Positive long eventId) throws EntityNotFoundException {
        log.info("Getting user request by user id:{}", userId);
        return authorizeService.getUserRequestsForThisEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestFullDto confirmOtherRequestForInitiatorEvent(@PathVariable @Positive long userId,
                                                               @PathVariable @Positive long eventId,
                                                               @PathVariable @Positive long reqId) throws AuthenticationException, EntityNotFoundException {
        log.info("Confirming request for event by user id:{}", userId);
        return authorizeService.confirmOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestFullDto rejectOtherRequestForInitiatorEvent(@PathVariable @Positive long userId,
                                                              @PathVariable @Positive long eventId,
                                                              @PathVariable @Positive long reqId) throws AuthenticationException, EntityNotFoundException {
        log.info("Rejecting request by user id:{}", userId);
        return authorizeService.rejectOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestFullDto> getUserRequestsForAllEvents(@PathVariable @Positive long userId) throws EntityNotFoundException {
        log.info("Getting all event`s requests by user id:{}", userId);
        return authorizeService.getUserRequestsForAllEvents(userId);
    }

    @PostMapping("/{userId}/requests")
    public RequestFullDto addUserRequestForThisEvent(@PathVariable @Positive long userId,
                                                     @RequestParam @Positive long eventId) throws EventStateException, AuthenticationException, EntityNotFoundException {
        log.info("Adding user`s request for event by user id:{}", userId);
        return authorizeService.addUserRequestForThisEvent(userId, eventId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public RequestFullDto rejectUserRequestForThisEvent(@PathVariable @Positive long userId,
                                                        @PathVariable @Positive long requestId) throws EntityNotFoundException {
        log.info("Rejecting request for event by user id:{}", userId);
        return authorizeService.rejectUserRequest(userId, requestId);
    }
}
