package ru.practicum.explorewithme.adminAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.dto.CommentFullDto;
import ru.practicum.explorewithme.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.model.CommentStatus;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.CommentStatusException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.EventStateException;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminCommentServiceImpl implements AdminCommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentFullDto> getAllUserComments(List<Long> users, List<Long> events, List<String> states, String text, Integer from, Integer size) throws EntityNotFoundException {
        if (eventRepository.findAllByIdWithoutPage(events).size() == 0) {
            throw new EntityNotFoundException("Can`t get all comments: event not found");
        }
        if (userRepository.findAllByIdWithoutPage(users).size() == 0) {
            throw new EntityNotFoundException("Can`t get all comments: user not found");
        }
        List<CommentStatus> statusList = new ArrayList<>();
        if (states != null) {
            for (String status : states) {
                try {
                    statusList.add(CommentStatus.valueOf(status));
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException("Can`t get all events: state '" + status + "' not found.");
                }
            }
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> commentList = commentRepository.getAllByUsersAndEventsAndStatesAndText(text, users, events, statusList, pageable);
        List<CommentFullDto> commentFullDtoList = new ArrayList<>();
        for (var comment : commentList) {
            commentFullDtoList.add(CommentMapper.commentToCommentFullDto(comment));
        }
        log.info("Getting succes");
        return commentFullDtoList;
    }

    @Override
    public CommentFullDto publishComment(Long eventId, Long commentId) throws EntityNotFoundException, CommentStatusException, EventStateException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t publish comment: event not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Can`t publish comment: comment not found"));
        if (!comment.getStatus().equals(CommentStatus.WAITING)) {
            throw new CommentStatusException("Can`t publish comment: comment state isn`t waiting");
        }
        if (event.getEventState() != EventState.PUBLISHED) {
            throw new EventStateException("Can`t publish comment: event state isn`t published");
        }
        comment.setPublishedOn(LocalDateTime.now());
        comment.setStatus(CommentStatus.PUBLISHED);
        commentRepository.saveAndFlush(comment);
        log.info("Publish success");
        return CommentMapper.commentToCommentFullDto(comment);
    }

    @Override
    public CommentFullDto rejectComment(Long eventId, Long commentId) throws EntityNotFoundException, CommentStatusException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t publish comment: event not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Can`t publish comment: comment not found"));
        if (!comment.getStatus().equals(CommentStatus.WAITING)) {
            throw new CommentStatusException("Can`t reject comment: comment state isn`t waiting");
        }
        comment.setStatus(CommentStatus.REJECTED);
        commentRepository.saveAndFlush(comment);
        log.info("Reject success");
        return CommentMapper.commentToCommentFullDto(comment);
    }

    @Override
    public void deleteComment(Long eventId, Long commentId) throws EntityNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Can`t delete comment: event not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Can`t delete comment: comment not found"));
        commentRepository.deleteById(commentId);
        log.info("Removing success");
    }
}
