package ru.practicum.explorewithme.authAPI.service;

import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.request.dto.RequestFullDto;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AuthRequestService {

    List<RequestFullDto> getUserRequestsForThisEvent(Long userId, Long eventId) throws EntityNotFoundException;

    RequestFullDto confirmOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException;

    RequestFullDto rejectOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException;

    List<RequestFullDto> getUserRequestsForAllEvents(Long id) throws EntityNotFoundException;

    RequestFullDto addUserRequestForThisEvent(Long userId, Long eventId) throws EntityNotFoundException, AuthenticationException, EventStateException;

    RequestFullDto rejectUserRequest(Long userId, Long requestId) throws EntityNotFoundException;
}
