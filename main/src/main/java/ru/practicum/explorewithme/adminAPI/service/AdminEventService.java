package ru.practicum.explorewithme.adminAPI.service;

import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAllEvents(List<Long> users,
                                    List<String> states,
                                    List<Long> categories,
                                    String rangeStart,
                                    String rangeEnd,
                                    Integer from,
                                    Integer size) throws EntityNotFoundException;

    EventFullDto updateEvent(Long id, EventDto eventDto) throws EntityNotFoundException;

    EventFullDto publishEvent(Long id) throws EntityNotFoundException, EventStateException;

    EventFullDto rejectEvent(Long id) throws EntityNotFoundException, EventStateException;
}
