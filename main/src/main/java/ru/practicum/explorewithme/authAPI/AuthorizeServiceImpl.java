package ru.practicum.explorewithme.authAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import ru.practicum.explorewithme.request.dto.RequestFullDto;
import ru.practicum.explorewithme.request.mapper.RequestMapper;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.request.model.RequestState;
import ru.practicum.explorewithme.request.repository.RequestRepository;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizeServiceImpl implements AuthorizeService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
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
        eventRepository.save(event);
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
        eventRepository.save(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
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
        eventRepository.save(event);
        log.info("Saving success");
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<RequestFullDto> getUserRequestsForThisEvent(Long userId, Long eventId) throws EntityNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t get user`s requests for event: event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t get user`s requests for event: user not found"));
        List<RequestFullDto> requestDtoList = new ArrayList<>();
        for (var request : requestRepository.getAllByUserIdAndEventId(userId, eventId)) {
            requestDtoList.add(RequestMapper.requestToRequestDto(request));
        }
        log.info("Getting success");
        return requestDtoList;
    }

    @Override
    public RequestFullDto confirmOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t confirm request: user not found"));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Can`t confirm request: request not found"));
        if (request.getState() != RequestState.PENDING) {
            throw new IllegalStateException("Can`t confirm request: request should be pending");
        }
        if (!request.getEvent().getId().equals(eventId)) {
            throw new IllegalArgumentException("Can`t confirm request: incorrect event`s id for request");
        }
        if (!request.getEvent().getInitiator().getId().equals(userId)) {
            throw new AuthenticationException("Can`t confirm request: only event`s initiator can confirm this request");
        }
        Event event = request.getEvent();
        if (event.getParticipantLimit() != 0 && (event.getParticipantLimit() - event.getConfirmedRequests()) <= 0) {
            requestRepository.rejectAllPendingRequest(eventId);
            throw new IllegalStateException("Can`t confirm request: event haven`t free slots");
        }
        if (request.getState() == RequestState.PENDING) {
            request.setState(RequestState.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            requestRepository.save(request);
        }
        log.info("Confirmed success");
        return RequestMapper.requestToRequestDto(request);
    }

    @Override
    public RequestFullDto rejectOtherRequestForInitiatorEvent(Long userId, Long eventId, Long requestId) throws EntityNotFoundException, AuthenticationException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t reject request: user not found"));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Can`t reject request: request not found"));
        if (!request.getEvent().getId().equals(eventId)) {
            throw new IllegalArgumentException("Can`t reject request: incorrect event`s id for request");
        }
        if (!request.getEvent().getInitiator().getId().equals(userId)) {
            throw new AuthenticationException("Can`t reject request: only event`s initiator can reject this request");
        }
        request.setState(RequestState.REJECTED);
        requestRepository.save(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }

    @Override
    public List<RequestFullDto> getUserRequestsForAllEvents(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can`t get all requests: user not found"));
        List<RequestFullDto> requestDtoList = new ArrayList<>();
        for (var request : requestRepository.getAllByRequesterId(id)) {
            requestDtoList.add(RequestMapper.requestToRequestDto(request));
        }
        log.info("Getting success");
        return requestDtoList;
    }

    @Override
    public RequestFullDto addUserRequestForThisEvent(Long userId, Long eventId) throws EntityNotFoundException, AuthenticationException, EventStateException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t add user`s request for event: event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t add user`s request for event: user not found"));
        if (event.getInitiator().getId().equals(userId)) {
            throw new AuthenticationException("Can`t add user`s request for event: event`s initiator can`t pull request to own event");
        }
        if (event.getParticipantLimit() != 0 && (event.getParticipantLimit() - event.getConfirmedRequests()) <= 0) {
            throw new IllegalStateException("Can`t add user`s request for event: event don`t have free slots");
        }

        RequestState requestState = RequestState.PENDING;
        if (!event.getRequestModeration()) {
            requestState = RequestState.CONFIRMED;
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        Request request = new Request(
                LocalDateTime.now(),
                event,
                user,
                requestState,
                null
        );
        requestRepository.save(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }

    @Override
    public RequestFullDto rejectUserRequest(Long userId, Long requestId) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t reject user`s request: user not found"));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Can`t reject user`s request: request not found"));
        request.setState(RequestState.CANCELED);
        requestRepository.save(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }
}
