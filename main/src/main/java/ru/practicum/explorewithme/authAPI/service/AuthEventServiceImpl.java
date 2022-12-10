package ru.practicum.explorewithme.authAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.dto.EventDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.mapper.EventMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthEventServiceImpl implements AuthEventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllEventsByUserId(Long userId, Integer from, Integer size) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t get all events by user`s id: user not found"));
        Pageable pageable = PageRequest.of(from / size, size);
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (var event : eventRepository.getAllByInitiatorId(userId, pageable)) {
            eventFullDtoList.add(EventMapper.eventToEventFullDto(event));
        }
        log.info("Getting success");
        return eventFullDtoList;
    }

    @Override
    public EventFullDto updateEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException, EventStateException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t update event by user`s id: user not found"));
        Event event = eventRepository.findById(eventDto.getEventId()).orElseThrow(() -> new EntityNotFoundException("Can`t update event by user`s id: event not found"));
        if (event.getEventState() == EventState.PUBLISHED) {
            throw new EventStateException("Can`t update event by user`s id: event is already published");
        } else if (event.getEventState() == EventState.REJECTED || event.getEventState() == EventState.CANCELED) {
            event.setEventState(EventState.PENDING);
        }
        if (eventDto.getEventDate() != null) {
            event.setDate(event.getDate());
        }
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
    public EventFullDto addEventByInitiator(Long userId, EventDto eventDto) throws EntityNotFoundException {
        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() -> new EntityNotFoundException("Can`t add event by user: category not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t add event by user: user not found"));
        if (eventDto.getPaid() == null) {
            throw new IllegalArgumentException("Can`t add event by user: paid isn`t initialized");
        }
        if (eventDto.getLocation() == null) {
            throw new IllegalArgumentException("Can`t add event by user: location isn`t initialized");
        }
        Event event = EventMapper.eventDtoToEvent(eventDto, categoryRepository.getReferenceById(eventDto.getCategory()));
        event.setCreated(LocalDateTime.now());
        event.setInitiator(userRepository.getReferenceById(userId));
        event.setEventState(EventState.PENDING);
        event.setConfirmedRequests(0L);
        event.setViews(0L);
        event.setComments(List.of());
        eventRepository.saveAndFlush(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventInfoByUserId(Long userId, Long eventId) throws EntityNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t get event by user`s id: event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t get event by user`s id: user not found"));
        log.info("Getting success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEventByInitiator(Long userId, Long eventId) throws EntityNotFoundException, EventStateException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t cancel event by user`s id: event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t cancel event by user`s id: user not found"));
        if (event.getEventState() != EventState.PENDING) {
            throw new EventStateException("Can`t cancel event by user`s id: event is not pending");
        }
        event.setEventState(EventState.CANCELED);
        eventRepository.saveAndFlush(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }
}
