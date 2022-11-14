package ru.practicum.explorewithme.authAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.request.dto.RequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    @Override
    public List<EventFullDto> getAllEventsByUserId(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateEventByInitiator(Long userId, EventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto addEventByInitiator(Long userId, EventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventInfoByUserId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEventByInitiator(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<RequestDto> getUserRequestsForThisEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public RequestDto confirmOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public RequestDto rejectOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public List<RequestDto> getUserRequestsForAllEvents(Long id) {
        return null;
    }

    @Override
    public RequestDto addUserRequestForThisEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public RequestDto rejectUserRequestForThisEvent(Long userId, Long eventId) {
        return null;
    }
}
