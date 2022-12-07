package ru.practicum.explorewithme.adminAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminEventServiceImpl implements AdminEventService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws EntityNotFoundException {
        if (categoryRepository.findAllByIdWithoutPage(categories).size() == 0) {
            throw new EntityNotFoundException("Can`t get all events: category not found");
        }
        if (userRepository.findAllByIdWithoutPage(users).size() == 0) {
            throw new EntityNotFoundException("Can`t get all events: user not found");
        }
        List<EventState> eventStates = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                try {
                    eventStates.add(EventState.valueOf(state));
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException("Can`t get all events: state '" + state + "' not found.");
                }
            }
        }
        LocalDateTime startDate = LocalDateTime.now();
        if (rangeStart != null) {
            startDate = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        LocalDateTime endDate = LocalDateTime.now();
        if (rangeStart != null) {
            endDate = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> eventList = eventRepository.getAllByUsersAndStatesAndCategoriesAndDates(users, eventStates, categories, startDate, endDate, pageable);
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event : eventList) {
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        log.info("Getting succes");
        return eventFullDtoList;
    }

    @Override
    public EventFullDto updateEvent(Long id, EventDto eventDto) throws EntityNotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t update event: event not found"));
        event.setAnnotation(eventDto.getAnnotation());
        event.setPaid(eventDto.getPaid());
        event.setDescription(eventDto.getDescription());
        event.setTitle(eventDto.getTitle());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        eventRepository.saveAndFlush(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto publishEvent(Long id) throws EntityNotFoundException, EventStateException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t publish event: event not found"));
        if (event.getEventState() != EventState.PENDING) {
            throw new EventStateException("Can`t publish event: event state isn`t pending");
        }
        event.setPublishedOn(LocalDateTime.now());
        event.setEventState(EventState.PUBLISHED);
        eventRepository.saveAndFlush(event);
        log.info("Publish success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto rejectEvent(Long id) throws EntityNotFoundException, EventStateException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t reject event: event not found"));
        if (event.getEventState() != EventState.PENDING) {
            throw new EventStateException("Can`t reject event: event state isn`t pending");
        }
        event.setEventState(EventState.CANCELED);
        eventRepository.saveAndFlush(event);
        log.info("Reject success");
        return EventMapper.eventToEventFullDto(event);
    }
}
