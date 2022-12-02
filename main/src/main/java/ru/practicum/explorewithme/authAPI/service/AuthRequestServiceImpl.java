package ru.practicum.explorewithme.authAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.event.model.Event;
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
@Transactional
public class AuthRequestServiceImpl implements AuthRequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    @Transactional(readOnly = true)
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
            requestRepository.saveAndFlush(request);
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
        requestRepository.saveAndFlush(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }

    @Override
    @Transactional(readOnly = true)
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
        requestRepository.saveAndFlush(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }

    @Override
    public RequestFullDto rejectUserRequest(Long userId, Long requestId) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can`t reject user`s request: user not found"));
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Can`t reject user`s request: request not found"));
        request.setState(RequestState.CANCELED);
        requestRepository.saveAndFlush(request);
        log.info("Saving success");
        return RequestMapper.requestToRequestDto(request);
    }
}
