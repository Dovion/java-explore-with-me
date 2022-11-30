package ru.practicum.explorewithme.authAPI.service;

import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.exception.ValidationException;

import java.util.List;

public interface AuthEventService {

    List<EventFullDto> getAllEventsByUserId(Long userId, Integer from, Integer size) throws EntityNotFoundException;

    EventFullDto updateEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException, EventStateException;

    EventFullDto addEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException, ValidationException;

    EventFullDto getEventInfoByUserId(Long userId, Long eventId) throws EntityNotFoundException;

    EventFullDto cancelEventByInitiator(Long userId, Long eventId) throws EntityNotFoundException, EventStateException;
}
