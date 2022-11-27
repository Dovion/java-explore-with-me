package ru.practicum.explorewithme.authAPI;

import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.request.dto.RequestFullDto;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AuthorizeService {

    List<EventFullDto> getAllEventsByUserId(Long userId, Integer from, Integer size) throws EntityNotFoundException;

    EventFullDto updateEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException, EventStateException;

    EventFullDto addEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException, ValidationException;

    EventFullDto getEventInfoByUserId(Long userId, Long eventId) throws EntityNotFoundException;

    EventFullDto cancelEventByInitiator(Long userId, Long eventId) throws EntityNotFoundException, EventStateException;

    List<RequestFullDto> getUserRequestsForThisEvent(Long userId, Long eventId) throws EntityNotFoundException;

    RequestFullDto confirmOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException;

    RequestFullDto rejectOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException;

    List<RequestFullDto> getUserRequestsForAllEvents(Long id) throws EntityNotFoundException;

    RequestFullDto addUserRequestForThisEvent(Long userId, Long eventId) throws EntityNotFoundException, AuthenticationException, EventStateException;

    RequestFullDto rejectUserRequest(Long userId, Long requestId) throws EntityNotFoundException;


}
