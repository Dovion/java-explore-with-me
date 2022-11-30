package ru.practicum.explorewithme.authAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.authAPI.service.AuthRequestService;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.request.dto.RequestFullDto;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class AuthRequestController {

    private final AuthRequestService authorizeService;

    @GetMapping("/{userId}/requests")
    public List<RequestFullDto> getUserRequestsForAllEvents(@PathVariable @Positive long userId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting all event`s requests by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.getUserRequestsForAllEvents(userId);
    }

    @PostMapping("/{userId}/requests")
    public RequestFullDto addUserRequestForThisEvent(@PathVariable @Positive long userId,
                                                     @RequestParam @Positive long eventId,
                                                     HttpServletRequest request) throws EventStateException, AuthenticationException, EntityNotFoundException {
        log.info("Adding user`s request for event by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.addUserRequestForThisEvent(userId, eventId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public RequestFullDto rejectUserRequestForThisEvent(@PathVariable @Positive long userId,
                                                        @PathVariable @Positive long requestId,
                                                        HttpServletRequest request) throws EntityNotFoundException {
        log.info("Rejecting request for event by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.rejectUserRequest(userId, requestId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestFullDto> getUserRequestsForThisEvent(@PathVariable @Positive long userId, @PathVariable @Positive long eventId, HttpServletRequest request) throws EntityNotFoundException {
        log.info("Getting user request by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.getUserRequestsForThisEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestFullDto confirmOtherRequestForInitiatorEvent(@PathVariable @Positive long userId,
                                                               @PathVariable @Positive long eventId,
                                                               @PathVariable @Positive long reqId,
                                                               HttpServletRequest request) throws AuthenticationException, EntityNotFoundException {
        log.info("Confirming request for event by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.confirmOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestFullDto rejectOtherRequestForInitiatorEvent(@PathVariable @Positive long userId,
                                                              @PathVariable @Positive long eventId,
                                                              @PathVariable @Positive long reqId,
                                                              HttpServletRequest request) throws AuthenticationException, EntityNotFoundException {
        log.info("Rejecting request by user id:{} at: {}", userId, request.getServletPath());
        return authorizeService.rejectOtherRequestForInitiatorEvent(userId, eventId, reqId);
    }
}
