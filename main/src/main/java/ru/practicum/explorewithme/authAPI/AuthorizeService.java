package ru.practicum.explorewithme.authAPI;

import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.request.dto.RequestDto;

import java.util.List;

public interface AuthorizeService {

    List<EventFullDto> getAllEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto updateEventByInitiator(Long userId, EventDto eventDto);

    EventFullDto addEventByInitiator(Long userId, EventDto eventDto);

    EventFullDto getEventInfoByUserId(Long userId, Long eventId);

    EventFullDto cancelEventByInitiator(Long userId, Long eventId);

    List<RequestDto> getUserRequestsForThisEvent(Long userId, Long eventId);

    RequestDto confirmOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId);

    RequestDto rejectOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId);

    List<RequestDto> getUserRequestsForAllEvents(Long id);

    RequestDto addUserRequestForThisEvent(Long userId, Long eventId);

    RequestDto rejectUserRequestForThisEvent(Long userId, Long eventId);




}
