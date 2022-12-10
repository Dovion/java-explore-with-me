package ru.practicum.explorewithme.publicAPI.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventPublicDto;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

public interface PublicEventService {

    List<EventPublicDto> getAllEvents(String text,
                                      List categories,
                                      Boolean paid,
                                      String rangeStart,
                                      String rangeEnd,
                                      Boolean onlyAvailable,
                                      String sort,
                                      Integer from,
                                      Integer size,
                                      HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request) throws EntityNotFoundException, URISyntaxException;
}
